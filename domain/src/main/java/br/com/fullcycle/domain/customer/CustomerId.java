package br.com.fullcycle.domain.customer;

import br.com.fullcycle.domain.exception.ValidationException;

import java.util.UUID;

public record CustomerId(String value) {

    public CustomerId {
        if (value == null) {
            throw new ValidationException("Invalid value for customerId");
        }
    }

    public static CustomerId unique() {
        return new CustomerId(UUID.randomUUID().toString());
    }

    public static CustomerId with(final String value) {
        try {
            return new CustomerId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for CustomerID");
        }
    }
}
