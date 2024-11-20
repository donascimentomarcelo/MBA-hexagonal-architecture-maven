package br.com.fullcycle.hexagonal.application.usecases.partner;

import br.com.fullcycle.hexagonal.application.domain.person.CNPJ;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.usecases.UseCase;
import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;


import java.util.Objects;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerRepository partnerJpaRepository;

    public CreatePartnerUseCase(final PartnerRepository partnerJpaRepository) {
        this.partnerJpaRepository = Objects.requireNonNull(partnerJpaRepository);
    }

    @Override
    public Output execute(final Input input) {
        if (partnerJpaRepository.partnerOfCNPJ(new CNPJ(input.cnpj)).isPresent()) {
            throw new ValidationException("Partner already exists");
        }
        if (partnerJpaRepository.partnerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = Partner.newPartner(input.name, input.cnpj, input.email);

        partner = partnerJpaRepository.create(partner);

        return new Output(
                partner.partnerId().value(),
                partner.cnpj().value(),
                partner.email().value(),
                partner.name().value());
    }

    public record Input(String cnpj, String email, String name) {

    }

    public record Output(String id, String cnpj, String email, String name) {

    }
}
