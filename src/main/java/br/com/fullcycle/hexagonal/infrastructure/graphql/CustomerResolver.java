package br.com.fullcycle.hexagonal.infrastructure.graphql;

import br.com.fullcycle.hexagonal.application.usecases.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.GetCustomerByIdUseCase;
import br.com.fullcycle.hexagonal.infrastructure.dtos.NewCustomerDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerResolver {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    public CustomerResolver(
            final CreateCustomerUseCase createCustomerUseCase,
            final GetCustomerByIdUseCase getCustomerByIdUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
    }

    @MutationMapping
    public CreateCustomerUseCase.Output createCustomer(@Argument NewCustomerDTO input) {
        var customer = new CreateCustomerUseCase.Input(input.cpf(), input.email(), input.name());
        return createCustomerUseCase.execute(customer);
    }

    @QueryMapping
    public GetCustomerByIdUseCase.Output customerOfId(@Argument String id) {
        return getCustomerByIdUseCase.execute(new GetCustomerByIdUseCase.Input(id)).orElse(null);
    }
}
