package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.infrastructure.models.Customer;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetCustomerByIdUseCaseTest {

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        final var expectedID = UUID.randomUUID().getMostSignificantBits();
        final var expectedCPF = "12345678";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";

        final var aCustomer = new Customer();
        aCustomer.setId(expectedID);
        aCustomer.setCpf(expectedCPF);
        aCustomer.setName(expectedName);
        aCustomer.setEmail(expectedEmail);

        final var input = new GetCustomerByIdUseCase.Input(expectedID);

        final var service = mock(CustomerService.class);
        when(service.findById(expectedID)).thenReturn(Optional.of(aCustomer));

        final var useCase = new GetCustomerByIdUseCase(service);
        final var output = useCase.execute(input).get();

        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCPF, output.cpf());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar um cliente por id")
    public void testGetByIdWithInvalid() throws Exception {

        final var expectedID = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetCustomerByIdUseCase.Input(expectedID);

        final var service = mock(CustomerService.class);
        when(service.findById(expectedID)).thenReturn(Optional.empty());

        final var useCase = new GetCustomerByIdUseCase(service);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}