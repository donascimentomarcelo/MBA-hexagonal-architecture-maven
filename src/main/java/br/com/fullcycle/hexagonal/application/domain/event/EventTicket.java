package br.com.fullcycle.hexagonal.application.domain.event;

import br.com.fullcycle.hexagonal.application.domain.ticket.TicketId;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;

public class EventTicket {

    private final TicketId ticketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;

    protected EventTicket(final TicketId ticketId, final EventId eventId, final CustomerId customerId,
                          final int ordering) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.ordering = ordering;
        this.customerId = customerId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public TicketId getTicketId() {
        return ticketId;
    }

    public EventId getEventId() {
        return eventId;
    }

    public int getOrdering() {
        return ordering;
    }
}
