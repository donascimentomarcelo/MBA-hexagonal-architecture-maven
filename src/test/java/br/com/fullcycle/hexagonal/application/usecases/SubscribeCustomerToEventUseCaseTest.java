package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.InMemoryCustomerRepository;
import br.com.fullcycle.hexagonal.application.InMemoryEventRepository;
import br.com.fullcycle.hexagonal.application.InMemoryTicketRepository;
import br.com.fullcycle.hexagonal.application.domain.Customer;
import br.com.fullcycle.hexagonal.application.domain.Event;
import br.com.fullcycle.hexagonal.application.domain.Partner;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.infrastructure.models.TicketStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscribeCustomerToEventUseCaseTest {

    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() throws Exception {

        final var expectedTicketsSize = 1;

        final var aPartner = Partner.newPartner("John", "41.543.345/0001-00", "john@email.com");
        final var anEvent = Event.newEvent("Disney on Ice", "2021-01-01", 10, aPartner);
        final var anCustomer = Customer.newCustomer("Jon Doe", "123.123.123-88", "jon@email.com");

        final var customerID = anCustomer.customerId().value();
        final var eventID = anEvent.eventId().value();

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        customerRepository.create(anCustomer);
        eventRepository.create(anEvent);

        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var output = useCase.execute(subscribeInput);

        assertEquals(eventID, output.eventId());
        assertNotNull(output.reservationDate());
        assertNotNull(output.ticketId());
        assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());

        final var event = eventRepository.eventOfId(anEvent.eventId());
        assertEquals(expectedTicketsSize, event.get().tickets().size());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento")
    public void shouldNotOrderATicket() throws Exception {

        final var expectedError = "Event not found";

        final var aPartner = Partner.newPartner("John", "41.543.345/0001-00", "john@email.com");
        final var anEvent = Event.newEvent("Disney on Ice", "2021-01-01", 10, aPartner);
        final var anCustomer = Customer.newCustomer("Jon Doe", "123.123.123-88", "jon@email.com");

        final var customerID = anCustomer.customerId().value();
        final var eventID = anEvent.eventId().value();

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        customerRepository.create(anCustomer);

        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var actualException = assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        assertEquals(expectedError, actualException.getMessage());
    }
}