package br.com.fullcycle.application.customer;


import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.customer.CustomerRepository;

import java.util.Objects;
import java.util.Optional;

public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    private final CustomerRepository customerJpaRepository;

    public GetCustomerByIdUseCase(final CustomerRepository customerJpaRepository) {
        this.customerJpaRepository = Objects.requireNonNull(customerJpaRepository);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return customerJpaRepository.customerOfId(CustomerId.with(input.id))
                .map(c -> new Output(c.customerId().value().toString(), c.cpf().value(), c.email().value(), c.name().value()));
    }

    public record Input(String id) {

    }

    public record Output(String id, String cpf, String email, String name) {

    }
}
