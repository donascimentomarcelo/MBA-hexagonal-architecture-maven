package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.application.usecases.CreateCustomerUseCase.Output;
import br.com.fullcycle.hexagonal.models.Customer;
import br.com.fullcycle.hexagonal.models.Partner;
import br.com.fullcycle.hexagonal.services.CustomerService;
import br.com.fullcycle.hexagonal.services.PartnerService;

import java.util.Objects;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerService service;

    public CreatePartnerUseCase(final PartnerService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Override
    public Output execute(final Input input) {
        if (service.findByCnpj(input.cnpj).isPresent()) {
            throw new ValidationException("Partner already exists");
        }
        if (service.findByEmail(input.email).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = new Partner();
        partner.setName(input.name);
        partner.setCnpj(input.cnpj);
        partner.setEmail(input.email);

        partner = service.save(partner);

        return new Output(partner.getId(), partner.getCnpj(), partner.getEmail(), partner.getName());
    }

    public record Input(String cnpj, String email, String name) {

    }

    public record Output(Long id, String cnpj, String email, String name) {

    }
}
