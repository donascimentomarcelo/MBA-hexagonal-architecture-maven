package br.com.fullcycle.domain.partner;

import br.com.fullcycle.domain.person.CNPJ;
import br.com.fullcycle.domain.person.Email;
import br.com.fullcycle.domain.person.Name;

public class Partner {

    private PartnerId partnerId;
    private Name name;
    private CNPJ cnpj;
    private Email email;

    public Partner(final PartnerId partnerId, final String name, final String cnpj, final String email) {
        this.partnerId = partnerId;
        this.name = new Name(name);
        this.cnpj = new CNPJ(cnpj);
        this.email = new Email(email);
    }

    public static Partner newPartner(String name, String cnpj, String email) {
        return new Partner(PartnerId.unique(), name, cnpj, email);
    }

    public PartnerId partnerId() {
        return partnerId;
    }

    public Name name() {
        return name;
    }

    public CNPJ cnpj() {
        return cnpj;
    }

    public Email email() {
        return email;
    }
}
