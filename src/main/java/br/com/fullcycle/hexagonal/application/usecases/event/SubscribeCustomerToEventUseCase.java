package br.com.fullcycle.hexagonal.application.usecases.event;

import br.com.fullcycle.hexagonal.application.usecases.UseCase;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.event.EventId;
import br.com.fullcycle.hexagonal.application.domain.ticket.Ticket;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.TicketRepository;


import java.time.Instant;
import java.util.Objects;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {

    private final CustomerRepository customerJpaRepository;
    private final EventRepository eventJpaRepository;
    private final TicketRepository ticketJpaRepository;

    public SubscribeCustomerToEventUseCase(
            final CustomerRepository customerJpaRepository,
            final EventRepository eventJpaRepository,
            final TicketRepository ticketJpaRepository
    ) {
        this.customerJpaRepository = Objects.requireNonNull(customerJpaRepository);
        this.eventJpaRepository = Objects.requireNonNull(eventJpaRepository);
        this.ticketJpaRepository = Objects.requireNonNull(ticketJpaRepository);
    }


    @Override
    public Output execute(final Input input) {
        var aCustomer = customerJpaRepository.customerOfId(CustomerId.with(input.customerId))
                .orElseThrow(() -> new ValidationException("Customer not found"));

        var anEvent = eventJpaRepository.eventOfId(EventId.with(input.eventId))
                .orElseThrow(() -> new ValidationException("Event not found"));

        Ticket ticket = anEvent.reserveTicket(aCustomer.customerId());

        ticketJpaRepository.create(ticket);
        eventJpaRepository.create(anEvent);

        return new Output(
                anEvent.eventId().value(),
                ticket.ticketId().value(),
                ticket.status().name(),
                ticket.reservedAt());
    }

    public record Input(String customerId, String eventId) {

    }

    public record Output(String eventId, String ticketId, String ticketStatus, Instant reservationDate) {

    }
}
