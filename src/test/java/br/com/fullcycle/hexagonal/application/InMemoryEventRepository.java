package br.com.fullcycle.hexagonal.application;


import br.com.fullcycle.hexagonal.application.domain.Event;
import br.com.fullcycle.hexagonal.application.domain.EventId;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryEventRepository implements EventRepository {

    private final Map<String, Event> event;

    public InMemoryEventRepository() {
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
}