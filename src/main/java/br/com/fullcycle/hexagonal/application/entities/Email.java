package br.com.fullcycle.hexagonal.application.entities;

import br.com.fullcycle.hexagonal.application.exception.ValidationException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("^[\\w\\-\\.]+@[\\w\\-\\.]+\\.\\w{2,3}$"))
            throw new ValidationException("Invalid value for Email");
    }
}
