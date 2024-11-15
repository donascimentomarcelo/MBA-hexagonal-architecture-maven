package br.com.fullcycle.hexagonal.infrastructure.rest;

import br.com.fullcycle.hexagonal.application.exception.ValidationException;
import br.com.fullcycle.hexagonal.application.usecases.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.GetCustomerByIdUseCase;
import br.com.fullcycle.hexagonal.infrastructure.dtos.NewCustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    public CustomerController(
            final CreateCustomerUseCase createCustomerUseCase,
            final GetCustomerByIdUseCase getCustomerByIdUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewCustomerDTO dto) {
        try {
            var customer = new CreateCustomerUseCase.Input(dto.cpf(), dto.email(), dto.name());
            final var output = createCustomerUseCase.execute(customer);
            return ResponseEntity.created(URI.create("/customers/" + output.id())).body(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return getCustomerByIdUseCase.execute(new GetCustomerByIdUseCase.Input(id))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}