package br.com.fullcycle.hexagonal.application.usecases.customer;

import br.com.fullcycle.hexagonal.application.repository.InMemoryCustomerJpaRepository;
import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class GetCustomerByIdUseCaseTest {

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        final var expectedCPF = "123.456.789-10";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";

        final var aCustomer = Customer.newCustomer(expectedName, expectedCPF, expectedEmail);

        final var customerRepository = new InMemoryCustomerJpaRepository();
        customerRepository.create(aCustomer);

        final var expectedID = aCustomer.customerId().value().toString();
        final var input = new GetCustomerByIdUseCase.Input(expectedID);

        final var useCase = new GetCustomerByIdUseCase(customerRepository);
        final var output = useCase.execute(input).get();

        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCPF, output.cpf());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar um cliente por id")
    public void testGetByIdWithInvalid() throws Exception {

        final var expectedID = UUID.randomUUID().toString();

        final var input = new GetCustomerByIdUseCase.Input(expectedID);

        final var customerRepository = new InMemoryCustomerJpaRepository();

        final var useCase = new GetCustomerByIdUseCase(customerRepository);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}