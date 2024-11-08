package br.com.fullcycle.hexagonal.graphql;

import br.com.fullcycle.hexagonal.application.usecases.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.CreatePartnerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.GetCustomerByIdUseCase;
import br.com.fullcycle.hexagonal.application.usecases.GetPartnerByIdUseCase;
import br.com.fullcycle.hexagonal.dtos.CustomerDTO;
import br.com.fullcycle.hexagonal.dtos.PartnerDTO;
import br.com.fullcycle.hexagonal.services.CustomerService;
import br.com.fullcycle.hexagonal.services.PartnerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class PartnerResolver {

    private final PartnerService service;

    public PartnerResolver(final PartnerService service) {
        this.service = Objects.requireNonNull(service);
    }

    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument PartnerDTO input) {
        final var useCase = new CreatePartnerUseCase(service);
        return useCase.execute(new CreatePartnerUseCase.Input(input.getCnpj(), input.getEmail(), input.getName()));
    }

    @QueryMapping
    public GetPartnerByIdUseCase.Output partnerOfId(@Argument Long id) {
        final var useCase = new GetPartnerByIdUseCase(service);
        return useCase.execute(new GetPartnerByIdUseCase.Input(id)).orElse(null);
    }
}
