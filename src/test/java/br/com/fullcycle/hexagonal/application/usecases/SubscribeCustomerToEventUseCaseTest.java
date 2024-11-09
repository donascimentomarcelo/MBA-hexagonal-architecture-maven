package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.models.Customer;
import br.com.fullcycle.hexagonal.models.Event;
import br.com.fullcycle.hexagonal.models.TicketStatus;
import br.com.fullcycle.hexagonal.services.CustomerService;
import br.com.fullcycle.hexagonal.services.EventService;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubscribeCustomerToEventUseCaseTest {

    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() throws Exception {

        final var expectedTicketsSize = 1;
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var aCustomer = new Customer();
        aCustomer.setId(customerID);
        aCustomer.setCpf("12345678901");
        aCustomer.setName("John Doe");
        aCustomer.setEmail("john.doe@gmail.com");

        final var aEvent = new Event();
        aEvent.setId(eventID);
        aEvent.setName("Disney");
        aEvent.setTotalSpots(10);

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(aCustomer.getId(), aEvent.getId());

        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(aCustomer));
        when(eventService.findById(eventID)).thenReturn(Optional.of(aEvent));
        when(eventService.findTicketByEventIdAndCustomerId(eventID, customerID)).thenReturn(Optional.empty());
        when(eventService.save(any())).thenAnswer(a -> {
            final var e = a.getArgument(0, Event.class);
            assertEquals(expectedTicketsSize, e.getTickets().size());
            return e;
        });

        final var useCase = new SubscribeCustomerToEventUseCase(eventService, customerService);
        final var output = useCase.execute(subscribeInput);

        assertEquals(eventID, output.eventId());
        assertNotNull(output.reservationDate());
        assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento")
    public void shouldNotOrderATicket() throws Exception {

        final var expectedError = "Event not found";
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var aCustomer = new Customer();
        aCustomer.setId(customerID);
        aCustomer.setCpf("12345678901");
        aCustomer.setName("John Doe");
        aCustomer.setEmail("john.doe@gmail.com");

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(aCustomer));
        when(eventService.findById(eventID)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(eventService, customerService);
        final var actualException = assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        assertEquals(expectedError,actualException.getMessage());
    }
}