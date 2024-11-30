package br.com.fullcycle.infrastructure.rest;

import br.com.fullcycle.application.partner.CreatePartnerUseCase;
import br.com.fullcycle.application.partner.GetPartnerByIdUseCase;
import br.com.fullcycle.domain.exception.ValidationException;
import br.com.fullcycle.infrastructure.dtos.NewPartnerDTO;
import br.com.fullcycle.infrastructure.http.HttpRouter;

import java.net.URI;

public class PartnerFnController {

    private final CreatePartnerUseCase createPartnerUseCase;
    private final GetPartnerByIdUseCase getPartnerByIdUseCase;

    public PartnerFnController(final CreatePartnerUseCase createPartnerUseCase, final GetPartnerByIdUseCase getPartnerByIdUseCase) {
        this.createPartnerUseCase = createPartnerUseCase;
        this.getPartnerByIdUseCase = getPartnerByIdUseCase;
    }

    public HttpRouter bind(final HttpRouter router) {
        router.GET("/partners/{id}", this::get);
        router.POST("/partners/{id}", this::create);
        return router;
    }

    private HttpRouter.HttpResponse<?> create(final HttpRouter.HttpRequest req) {
        try {
            final var dto = req.body(NewPartnerDTO.class);
            var partner = new CreatePartnerUseCase.Input(dto.cnpj(), dto.email(), dto.name());
            final var output = createPartnerUseCase.execute(partner);
            return HttpRouter.HttpResponse.created(URI.create("/partners" + output.id())).body(output);
        } catch (ValidationException ex) {
            return HttpRouter.HttpResponse.unprocessableEntity().body(ex.getMessage());
        }
    }

    private HttpRouter.HttpResponse<?> get(final HttpRouter.HttpRequest req) {
        final String id = req.pathParam("id");
        return getPartnerByIdUseCase.execute(new GetPartnerByIdUseCase.Input(id))
                .map(HttpRouter.HttpResponse::ok)
                .orElseGet(HttpRouter.HttpResponse.notFound()::build);
    }
}
