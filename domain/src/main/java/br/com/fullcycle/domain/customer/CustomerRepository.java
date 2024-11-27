package br.com.fullcycle.domain.customer;


import br.com.fullcycle.domain.person.CPF;
import br.com.fullcycle.domain.person.Email;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> customerOfId(CustomerId anId);

    Optional<Customer> customerOfCPF(CPF cpf);

    Optional<Customer> customerOfEmail(Email email);

    Customer create(Customer customer);

    Customer update(Customer customer);

    void deleteAll();
}
