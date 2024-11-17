package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.domain.Customer;
import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;

import jakarta.inject.Named;

@Named
public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.Input, CreateCustomerUseCase.Output> {

    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Output execute(final Input input) {
        if (customerRepository.customerOfCPF(input.cpf).isPresent()) {
            throw new ValidationException("Customer already exists");
        }
        if (customerRepository.customerOfEmail(input.email).isPresent()) {
            throw new ValidationException("Customer already exists");
        }

        var customer = Customer.newCustomer(input.name, input.cpf, input.email);

        customer = customerRepository.create(customer);

        final var uuid = customer.customerId().value().toString();
        return new Output(uuid, customer.cpf().value(), customer.email().value(), customer.name().value());
    }

    public record Input(String cpf, String email, String name) {

    }

    public record Output(String id, String cpf, String email, String name) {

    }
}
