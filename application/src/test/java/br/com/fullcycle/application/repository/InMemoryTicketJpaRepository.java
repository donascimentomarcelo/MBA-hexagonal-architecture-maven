package br.com.fullcycle.application.repository;


import br.com.fullcycle.domain.ticket.Ticket;
import br.com.fullcycle.domain.ticket.TicketId;
import br.com.fullcycle.domain.ticket.TicketRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryTicketJpaRepository implements TicketRepository {

    private final Map<String, Ticket> tickets;

    public InMemoryTicketJpaRepository() {
        this.tickets = new HashMap<>();
    }

    @Override
    public Optional<Ticket> ticketOfId(TicketId anId) {
        return Optional.ofNullable(this.tickets.get(Objects.requireNonNull(anId).value()));
    }

    @Override
    public Ticket create(Ticket ticket) {
        this.tickets.put(ticket.ticketId().value(), ticket);
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        this.tickets.put(ticket.ticketId().value(), ticket);
        return ticket;
    }

    @Override
    public void deleteAll() {
        this.tickets.clear();
    }
}