package br.com.fullcycle.application.event;

import br.com.fullcycle.application.repository.InMemoryEventJpaRepository;
import br.com.fullcycle.application.repository.InMemoryPartnerJpaRepository;
import br.com.fullcycle.domain.exception.ValidationException;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.partner.PartnerId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateEventUseCaseTest {

    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() throws Exception {

        // given
        final var aPartner = Partner.newPartner("John", "41.543.345/0001-00", "john@email.com");
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 10;
        final var expectedPartnerId = aPartner.partnerId();

        final var createInput =
                new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId.value(), expectedTotalSpots);

        // when
        final var eventRepository = new InMemoryEventJpaRepository();
        final var partnerRepository = new InMemoryPartnerJpaRepository();

        partnerRepository.create(aPartner);

        final var useCase = new CreateEventUseCase(eventRepository, partnerRepository);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedDate, output.date());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedTotalSpots, output.totalSpots());
        Assertions.assertEquals(expectedPartnerId.value(), output.partnerId());
    }

    @Test
    @DisplayName("Não deve criar um evento quando parceiro for vazio")
    public void testCreate_whenPartnerIsEmpty() throws Exception {

        // given
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 10;
        final var expectedPartnerId = PartnerId.unique().value();
        final var expectedError = "Partner not found";

        final var createInput =
                new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);

        // when
        final var eventRepository = new InMemoryEventJpaRepository();
        final var partnerRepository = new InMemoryPartnerJpaRepository();

        final var useCase = new CreateEventUseCase(eventRepository, partnerRepository);
        final var actualError = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}