package br.com.fullcycle.application.repository;


import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryEventJpaRepository implements EventRepository {

    private final Map<String, Event> event;

    public InMemoryEventJpaRepository() {
        this.event = new HashMap<>();
    }

    @Override
    public Optional<Event> eventOfId(EventId anId) {
        return Optional.ofNullable(this.event.get(Objects.requireNonNull(anId).value()));
    }

    @Override
    public Event create(Event event) {
        this.event.put(event.eventId().value(), event);
        return event;
    }

    @Override
    public Event update(Event event) {
        this.event.put(event.eventId().value(), event);
        return event;
    }

    @Override
    public void deleteAll() {
        this.event.clear();
    }

}