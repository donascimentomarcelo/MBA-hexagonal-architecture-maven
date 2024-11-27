package br.com.fullcycle.application.partner;

import br.com.fullcycle.application.repository.InMemoryPartnerJpaRepository;
import br.com.fullcycle.domain.partner.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("Não deve cadastrar um parceiro com CNPJ duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        final var expectedCNPJ = "58.554.933/0001-00";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";

        final var repository = new InMemoryPartnerJpaRepository();
        final var aPartner = Partner.newPartner(
                expectedName,
                expectedCNPJ,
                expectedEmail);
        repository.create(aPartner);

        var expectedID = aPartner.partnerId().value();
        final var input = new GetPartnerByIdUseCase.Input(expectedID);


        final var useCase = new GetPartnerByIdUseCase(repository);
        final var output = useCase.execute(input).get();

        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar um parceiro por id")
    public void testGetByIdWithInvalid() throws Exception {
        final var expectedID = UUID.randomUUID().toString();

        final var input = new GetPartnerByIdUseCase.Input(expectedID);

        final var repository = new InMemoryPartnerJpaRepository();
        final var useCase = new GetPartnerByIdUseCase(repository);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}