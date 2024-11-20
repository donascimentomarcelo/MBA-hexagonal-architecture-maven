package br.com.fullcycle.hexagonal.application.domain.customer;

import br.com.fullcycle.hexagonal.application.domain.person.CPF;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.domain.person.Name;

public class Customer {

    private CustomerId customerId;
    private Name name;
    private CPF cpf;
    private Email email;

    public Customer(final CustomerId customerId, final String name, final String cpf, final String email) {
        this.customerId = customerId;
        this.setName(name);
        this.setCpf(cpf);
        this.setEmail(email);
    }

    public static Customer newCustomer(String name, String cpf, String email) {
        return new Customer(CustomerId.unique(), name, cpf, email);
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Name name() {
        return name;
    }

    public CPF cpf() {
        return cpf;
    }

    public Email email() {
        return email;
    }

    private void setName(final String name) {
        this.name = new Name(name);
    }

    private void setCpf(final String cpf) {
        this.cpf = new CPF(cpf);
    }

    private void setEmail(final String email) {
        this.email = new Email(email);
    }
}
