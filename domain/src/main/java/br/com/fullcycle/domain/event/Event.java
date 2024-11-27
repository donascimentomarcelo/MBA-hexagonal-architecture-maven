package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.person.Name;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.partner.PartnerId;
import br.com.fullcycle.domain.ticket.Ticket;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Event {
    private final EventId eventId;
    private Name name;
    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;
    private Set<EventTicket> tickets;

    public Event(
            final EventId eventId,
            final String name,
            final String date,
            final int totalSpots,
            final PartnerId partnerId,
            final Set<EventTicket> tickets) {
        this(eventId, tickets);
        this.name = new Name(name);
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        this.totalSpots = totalSpots;
        this.partnerId = partnerId;
    }

    private Event(final EventId eventId, final Set<EventTicket> tickets) {
        this.eventId = eventId;
        this.tickets = tickets != null ? tickets : new HashSet<>(0);
    }

    public static Event newEvent(final String name,
                                 final String date,
                                 final int totalSpots,
                                 final Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.partnerId(), null);
    }

    public static Event restore(
            final String id,
            final String name,
            final String date,
            final int totalSpots,
            final String partnerId,
            final Set<EventTicket> tickets) {
        return new Event(EventId.with(id), name, date, totalSpots, PartnerId.with(partnerId), tickets);
    }

    public EventId eventId() {
        return eventId;
    }

    public Name name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public PartnerId partnerId() {
        return partnerId;
    }

    public Set<EventTicket> tickets() {
        return Collections.unmodifiableSet(tickets);
    }


    public Ticket reserveTicket(final CustomerId customerId) {
        this.tickets().stream()
                .filter(it -> Objects.equals(it.customerId(), customerId))
                .findFirst()
                .ifPresent(it -> {
                    throw new ValidationException("Email already registered");
                });

        if (totalSpots() < tickets().size() + 1) {
            throw new ValidationException("Event sold out");
        }

        var newTicket = Ticket.newTicket(customerId, eventId());

        this.tickets.add(new EventTicket(newTicket.ticketId(), eventId(), customerId, tickets().size() + 1));
        return newTicket;
    }
}
