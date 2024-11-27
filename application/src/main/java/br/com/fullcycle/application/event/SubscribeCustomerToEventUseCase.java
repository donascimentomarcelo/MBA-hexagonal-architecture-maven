package br.com.fullcycle.application.event;


import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.domain.exception.ValidationException;
import br.com.fullcycle.domain.ticket.Ticket;
import br.com.fullcycle.domain.ticket.TicketRepository;

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
