package br.com.fullcycle.application.partner;


import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.exception.ValidationException;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.partner.PartnerRepository;
import br.com.fullcycle.domain.person.CNPJ;
import br.com.fullcycle.domain.person.Email;

import java.util.Objects;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerRepository partnerRepository;

    public CreatePartnerUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Output execute(final Input input) {
        if (partnerRepository.partnerOfCNPJ(new CNPJ(input.cnpj)).isPresent()) {
            throw new ValidationException("Partner already exists");
        }
        if (partnerRepository.partnerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = Partner.newPartner(input.name, input.cnpj, input.email);

        partner = partnerRepository.create(partner);

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
