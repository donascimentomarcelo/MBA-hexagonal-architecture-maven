package br.com.fullcycle.application.partner;

import br.com.fullcycle.application.repository.InMemoryPartnerJpaRepository;
import br.com.fullcycle.domain.exception.ValidationException;
import br.com.fullcycle.domain.partner.Partner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CreatePartnerTest {

    @Test
    @DisplayName("Deve criar um parceiro")
    public void testCreate() throws Exception {
        final var expectedCNPJ = "41.543.345/0001-00";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var repository = new InMemoryPartnerJpaRepository();
        final var useCase = new CreatePartnerUseCase(repository);
        final var output = useCase.execute(createInput);

        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCNPJShouldFail() throws Exception {
        final var expectedCNPJ = "58.554.933/0001-00";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";
        final var expectedError = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var aPartner = Partner.newPartner(expectedName, "58.554.933/0002-00", expectedEmail);

        final var repository = new InMemoryPartnerJpaRepository();
        repository.create(aPartner);

        final var useCase = new CreatePartnerUseCase(repository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}
