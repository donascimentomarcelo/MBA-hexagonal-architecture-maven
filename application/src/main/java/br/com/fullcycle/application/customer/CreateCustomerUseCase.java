package br.com.fullcycle.application.customer;


import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.exception.ValidationException;
import br.com.fullcycle.domain.person.CPF;
import br.com.fullcycle.domain.person.Email;
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
