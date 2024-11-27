package br.com.fullcycle.domain.ticket;

import br.com.fullcycle.domain.exception.ValidationException;

import java.util.UUID;

public record TicketId(String value) {

    public TicketId {
        if (value == null)
            throw new ValidationException("Invalid value for ticketId");
    }

    public static TicketId unique() {
        return new TicketId(UUID.randomUUID().toString());
    }

    public static TicketId with(final String value) {
        try {
            return new TicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for ticketId");
        }
    }
}
