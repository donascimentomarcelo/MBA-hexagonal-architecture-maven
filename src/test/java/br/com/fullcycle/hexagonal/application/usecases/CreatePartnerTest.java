package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.models.Partner;
import br.com.fullcycle.hexagonal.services.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class CreatePartnerTest {

    @Test
    @DisplayName("Deve criar um parceiro")
    public void testCreate() throws Exception {
        final var expectedCNPJ = "58554933000100";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var customerService = Mockito.mock(PartnerService.class);
        when(customerService.findByCnpj(expectedCNPJ)).thenReturn(Optional.empty());
        when(customerService.findByEmail(expectedEmail)).thenReturn(Optional.empty());
        when(customerService.save(any())).thenAnswer(a -> {
            var customer = a.getArgument(0, Partner.class);
            customer.setId(UUID.randomUUID().getMostSignificantBits());
            return customer;
        });

        final var useCase = new CreatePartnerUseCase(customerService);
        final var output = useCase.execute(createInput);

        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCNPJShouldFail() throws Exception {
        final var expectedCNPJ = "58554933000100";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";
        final var expectedError = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var aPartner = new Partner();
        aPartner.setId(UUID.randomUUID().getMostSignificantBits());
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setName(expectedName);
        aPartner.setEmail(expectedEmail);

        final var customerService = Mockito.mock(PartnerService.class);
        when(customerService.findByCnpj(expectedCNPJ)).thenReturn(Optional.of(aPartner));

        final var useCase = new CreatePartnerUseCase(customerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}
