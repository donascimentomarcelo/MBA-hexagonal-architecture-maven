package br.com.fullcycle.hexagonal.application.domain;

import br.com.fullcycle.hexagonal.application.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Event {
    private final EventId eventId;
    private Name name;
    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;
    private Set<EventTicket> tickets;

    public Event(final EventId eventId, final String name, final String date, final int totalSpots,
                 final PartnerId partnerId) {
        this(eventId);
        this.name = new Name(name);
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        this.totalSpots = totalSpots;
        this.partnerId = partnerId;
    }

    private Event(final EventId eventId) {
        this.eventId = eventId;
        this.tickets = new HashSet<>(0);
    }

    public static Event newEvent(final String name,
                                 final String date,
                                 final int totalSpots,
                                 final Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.partnerId());
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
                .filter(it -> Objects.equals(it.getCustomerId(), customerId))
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
