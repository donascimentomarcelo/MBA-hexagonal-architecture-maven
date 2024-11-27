package br.com.fullcycle.domain.customer;

import br.com.fullcycle.domain.person.CPF;
import br.com.fullcycle.domain.person.Email;
import br.com.fullcycle.domain.person.Name;

public class Customer {

    private br.com.fullcycle.domain.customer.CustomerId customerId;
    private Name name;
    private CPF cpf;
    private Email email;

    public Customer(final br.com.fullcycle.domain.customer.CustomerId customerId, final String name, final String cpf, final String email) {
        this.customerId = customerId;
        this.setName(name);
        this.setCpf(cpf);
        this.setEmail(email);
    }

    public static Customer newCustomer(String name, String cpf, String email) {
        return new Customer(br.com.fullcycle.domain.customer.CustomerId.unique(), name, cpf, email);
    }

    public br.com.fullcycle.domain.customer.CustomerId customerId() {
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
