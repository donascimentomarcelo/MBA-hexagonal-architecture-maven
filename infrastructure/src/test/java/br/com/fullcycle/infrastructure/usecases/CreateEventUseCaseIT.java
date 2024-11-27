package br.com.fullcycle.infrastructure.usecases;

import br.com.fullcycle.application.event.CreateEventUseCase;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.domain.exception.ValidationException;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.partner.PartnerId;
import br.com.fullcycle.domain.partner.PartnerRepository;
import br.com.fullcycle.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CreateEventUseCaseIT extends IntegrationTest {

    @Autowired
    private CreateEventUseCase useCase;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @BeforeEach
    void tearDown() {
        eventRepository.deleteAll();
        partnerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() throws Exception {

        // given
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 10;

        Partner partner = createPartner();

        final var createInput =
                new CreateEventUseCase.Input(expectedDate, expectedName, partner.partnerId().value(), expectedTotalSpots);

        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedDate, output.date());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedTotalSpots, output.totalSpots());
        Assertions.assertEquals(partner.partnerId().value(), output.partnerId());
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

        final var actualError = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    private Partner createPartner() {

        final var partner = Partner.newPartner("Joe McAlister", "58.554.933/0001-00", "email@email.com");
        return partnerRepository.create(partner);
    }
}