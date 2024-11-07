package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.models.Partner;
import br.com.fullcycle.hexagonal.services.PartnerService;

import java.util.Objects;
import java.util.Optional;

public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input,  Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerService service;

    public GetPartnerByIdUseCase(final PartnerService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return service.findById(input.id)
                .map(p -> new Output(p.getId(), p.getCnpj(), p.getEmail(), p.getName()));
    }

    public record Input(Long id) {

    }

    public record Output(Long id, String cnpj, String email, String name) {

    }
}
