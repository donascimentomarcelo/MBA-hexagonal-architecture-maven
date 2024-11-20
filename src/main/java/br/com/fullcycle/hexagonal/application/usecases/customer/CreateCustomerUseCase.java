package br.com.fullcycle.hexagonal.application.usecases.customer;

import br.com.fullcycle.hexagonal.application.domain.person.CPF;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.usecases.UseCase;
import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;

import jakarta.inject.Named;

@Named
public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.Input, CreateCustomerUseCase.Output> {

    private final CustomerRepository customerJpaRepository;

    public CreateCustomerUseCase(final CustomerRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Output execute(final Input input) {
        if (customerJpaRepository.customerOfCPF(new CPF(input.cpf)).isPresent()) {
            throw new ValidationException("Customer already exists");
        }
        if (customerJpaRepository.customerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Customer already exists");
        }

        var customer = Customer.newCustomer(input.name, input.cpf, input.email);

        customer = customerJpaRepository.create(customer);

        final var uuid = customer.customerId().value().toString();
        return new Output(uuid, customer.cpf().value(), customer.email().value(), customer.name().value());
    }

    public record Input(String cpf, String email, String name) {

    }

    public record Output(String id, String cpf, String email, String name) {

    }
}
