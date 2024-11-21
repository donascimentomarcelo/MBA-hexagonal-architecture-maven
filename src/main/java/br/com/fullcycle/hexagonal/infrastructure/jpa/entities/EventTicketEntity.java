package br.com.fullcycle.hexagonal.infrastructure.jpa.entities;

import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.event.EventId;
import br.com.fullcycle.hexagonal.application.domain.event.EventTicket;
import br.com.fullcycle.hexagonal.application.domain.ticket.Ticket;
import br.com.fullcycle.hexagonal.application.domain.ticket.TicketId;
import br.com.fullcycle.hexagonal.application.domain.ticket.TicketStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "EventTicket")
@Table(name = "events_tickets")
public class EventTicketEntity {

    @Id
    private UUID ticketId;

    private UUID customerId;

    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventEntity event;

    public EventTicketEntity() {
    }

    public EventTicketEntity(
            UUID ticketId,
            UUID customerId,
            int ordering,
            EventEntity event) {
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.ordering = ordering;
        this.event = event;
    }

    public EventTicket toEventTicket() {
        return new EventTicket(
                TicketId.with(this.ticketId.toString()),
                EventId.with(this.event.id().toString()),
                CustomerId.with(this.customerId.toString()),
                this.ordering
        );
    }

    public static EventTicketEntity of(final EventEntity event, final EventTicket ev) {
        return new EventTicketEntity(
                UUID.fromString(ev.ticketId().value()),
                UUID.fromString(ev.customerId().value()),
                ev.ordering(),
                event
        );
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(final UUID ticketId) {
        this.ticketId = ticketId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final UUID customerId) {
        this.customerId = customerId;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(final int ordering) {
        this.ordering = ordering;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(final EventEntity event) {
        this.event = event;
    }
}
