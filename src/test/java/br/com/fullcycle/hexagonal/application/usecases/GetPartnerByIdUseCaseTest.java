package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.models.Partner;
import br.com.fullcycle.hexagonal.services.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("Não deve cadastrar um parceiro com CPF duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        final var expectedID = UUID.randomUUID().getMostSignificantBits();
        final var expectedCPF = "58554933000100";
        final var expectedEmail = "email@email.com";
        final var expectedName = "Joe McAlister";

        final var aPartner = new Partner();
        aPartner.setId(expectedID);
        aPartner.setCnpj(expectedCPF);
        aPartner.setName(expectedName);
        aPartner.setEmail(expectedEmail);

        final var input = new GetPartnerByIdUseCase.Input(expectedID);

        final var service = mock(PartnerService.class);
        when(service.findById(expectedID)).thenReturn(Optional.of(aPartner));

        final var useCase = new GetPartnerByIdUseCase(service);
        final var output = useCase.execute(input).get();

        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCPF, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar um parceiro por id")
    public void testGetByIdWithInvalid() throws Exception {

        final var expectedID = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetPartnerByIdUseCase.Input(expectedID);

        final var service = mock(PartnerService.class);
        when(service.findById(expectedID)).thenReturn(Optional.empty());

        final var useCase = new GetPartnerByIdUseCase(service);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}