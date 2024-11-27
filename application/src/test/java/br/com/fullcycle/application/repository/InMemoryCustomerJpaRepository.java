package br.com.fullcycle.application.repository;


import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.person.CPF;
import br.com.fullcycle.domain.person.Email;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCustomerJpaRepository implements CustomerRepository {

    private final Map<String, Customer> customers;
    private final Map<String, Customer> customersByCPF;
    private final Map<String, Customer> customersByEmail;

    public InMemoryCustomerJpaRepository() {
        this.customers = new HashMap<>();
        this.customersByCPF = new HashMap<>();
        this.customersByEmail = new HashMap<>();
    }

    @Override
    public Optional<Customer> customerOfId(CustomerId anId) {
        return Optional.ofNullable(this.customers.get(Objects.requireNonNull(anId).value()));
    }

    @Override
    public Optional<Customer> customerOfCPF(CPF cpf) {
        return Optional.ofNullable(this.customersByCPF.get(Objects.requireNonNull(cpf.value())));
    }

    @Override
    public Optional<Customer> customerOfEmail(Email email) {
        return Optional.ofNullable(this.customersByEmail.get(Objects.requireNonNull(email.value())));
    }

    @Override
    public Customer create(Customer customer) {
        this.customers.put(customer.customerId().value(), customer);
        this.customersByCPF.put(customer.cpf().value(), customer);
        this.customersByEmail.put(customer.email().value(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        this.customers.put(customer.customerId().value(), customer);
        this.customersByCPF.put(customer.cpf().value(), customer);
        this.customersByEmail.put(customer.email().value(), customer);
        return customer;
    }

    @Override
    public void deleteAll() {
        this.customers.clear();
    }
}