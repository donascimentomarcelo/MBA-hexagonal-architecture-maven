package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;

import java.util.Objects;
import java.util.Optional;

public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    private final CustomerService service;

    public GetCustomerByIdUseCase(final CustomerService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return service.findById(input.id)
                .map(c -> new Output(c.getId(), c.getCpf(), c.getEmail(), c.getName()));
    }

    public record Input(Long id) {

    }

    public record Output(Long id, String cpf, String email, String name) {

    }
}
