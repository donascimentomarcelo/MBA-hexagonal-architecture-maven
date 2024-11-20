package br.com.fullcycle.hexagonal.application.usecases.partner;

import br.com.fullcycle.hexagonal.application.usecases.UseCase;
import br.com.fullcycle.hexagonal.application.domain.partner.PartnerId;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;

import java.util.Objects;
import java.util.Optional;

public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerRepository partnerJpaRepository;

    public GetPartnerByIdUseCase(final PartnerRepository partnerJpaRepository) {
        this.partnerJpaRepository = Objects.requireNonNull(partnerJpaRepository);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return partnerJpaRepository.partnerOfId(PartnerId.with(input.id))
                .map(partner -> new Output(
                        partner.partnerId().value().toString(),
                        partner.cnpj().value(),
                        partner.email().value(),
                        partner.name().value()
                ));
    }

    public record Input(String id) {

    }

    public record Output(String id, String cnpj, String email, String name) {

    }
}
