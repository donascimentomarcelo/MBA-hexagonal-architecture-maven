package br.com.fullcycle.infrastructure.jpa.entities;


import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventTicket;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "Event")
@Table(name = "events")
public class EventEntity {

    @Id
    private UUID id;

    private String name;

    private String date;

    private int totalSpots;

    private UUID partnerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
    private Set<EventTicketEntity> tickets;

    public EventEntity() {
        this.tickets = new HashSet<>();
    }

    public EventEntity(UUID id, String name, String date, int totalSpots, UUID partnerId) {
        this();
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalSpots = totalSpots;
        this.partnerId = partnerId;
    }

    public static EventEntity of(final Event event) {
        var entity = new EventEntity(
                UUID.fromString(event.eventId().value()),
                event.name().value(),
                event.date().toString(),
                event.totalSpots(),
                UUID.fromString(event.partnerId().value())
        );

        event.tickets().forEach(entity::addTicket);

        return entity;
    }

    private void addTicket(final EventTicket ticket) {
        this.tickets.add(EventTicketEntity.of(this, ticket));
    }

    public Event toEvent() {
        return Event.restore(
                this.id().toString(),
                this.name(),
                this.date(),
                this.totalSpots(),
                this.partnerId().toString(),
                this.tickets().stream()
                        .map(EventTicketEntity::toEventTicket)
                        .collect(Collectors.toSet())
        );
    }

    public UUID id() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String date() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(final int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public UUID partnerId() {
        return partnerId;
    }

    public void setPartnerId(final UUID partnerId) {
        this.partnerId = partnerId;
    }

    public Set<EventTicketEntity> tickets() {
        return tickets;
    }

    public void setTickets(final Set<EventTicketEntity> tickets) {
        this.tickets = tickets;
    }
}
