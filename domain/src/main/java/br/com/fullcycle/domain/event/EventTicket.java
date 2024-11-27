package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.ticket.TicketId;
import br.com.fullcycle.domain.customer.CustomerId;

public class EventTicket {

    private final TicketId ticketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;

    public EventTicket(final TicketId ticketId, final EventId eventId, final CustomerId customerId,
                       final int ordering) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.ordering = ordering;
        this.customerId = customerId;
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public EventId eventId() {
        return eventId;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public int ordering() {
        return ordering;
    }
}
