package br.com.fullcycle.hexagonal.application.usecases.event;

import br.com.fullcycle.hexagonal.application.usecases.UseCase;
import br.com.fullcycle.hexagonal.application.domain.event.Event;
import br.com.fullcycle.hexagonal.application.domain.partner.PartnerId;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;

import java.util.Objects;

public class CreateEventUseCase extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {

    private final EventRepository eventJpaRepository;
    private final PartnerRepository partnerJpaRepository;

    public CreateEventUseCase(final EventRepository eventJpaRepository, final PartnerRepository partnerJpaRepository) {
        this.eventJpaRepository = Objects.requireNonNull(eventJpaRepository);
        this.partnerJpaRepository = Objects.requireNonNull(partnerJpaRepository);
    }

    @Override
    public Output execute(final Input input) {
        final var aPartner = partnerJpaRepository.partnerOfId(PartnerId.with(input.partnerId))
                .orElseThrow(() -> new ValidationException("Partner not found"));
        final var event = Event.newEvent(input.name, input.date, input.totalSpots, aPartner);

        var anEvent = eventJpaRepository.create(event);

        return new Output(
                anEvent.eventId().value(),
                input.date,
                anEvent.name().value(),
                anEvent.totalSpots(),
                anEvent.partnerId().value());
    }

    public record Input(String date, String name, String partnerId, Integer totalSpots) {

    }

    public record Output(String id, String date, String name, int totalSpots, String partnerId) {

    }
}
