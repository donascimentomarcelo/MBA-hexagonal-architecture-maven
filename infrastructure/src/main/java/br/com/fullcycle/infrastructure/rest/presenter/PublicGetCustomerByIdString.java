package br.com.fullcycle.infrastructure.rest.presenter;

import br.com.fullcycle.application.Presenter;
import br.com.fullcycle.application.customer.GetCustomerByIdUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("publicGetCustomer")
public class PublicGetCustomerByIdString implements Presenter<Optional<GetCustomerByIdUseCase.Output>, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicGetCustomerByIdString.class);

    @Override
    public String present(final Optional<GetCustomerByIdUseCase.Output> output) {
        return output.map(GetCustomerByIdUseCase.Output::id)
                .orElseGet(() -> "not found");
    }

    @Override
    public String present(final Throwable error) {
        LOGGER.error("An error was observer at GetCustomerByIdUseCase", error);
        return "not found";
    }
}
