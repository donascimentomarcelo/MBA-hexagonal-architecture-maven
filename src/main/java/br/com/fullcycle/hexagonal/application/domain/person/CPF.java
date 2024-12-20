package br.com.fullcycle.hexagonal.application.domain.person;

import br.com.fullcycle.hexagonal.application.exception.ValidationException;

public record CPF(String value) {

    public CPF {
        if (value == null || !value.matches("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$"))
            throw new ValidationException("Invalid value for CPF");
    }
}
