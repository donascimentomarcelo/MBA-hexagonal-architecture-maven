package br.com.fullcycle.domain.partner;


import br.com.fullcycle.domain.person.CNPJ;
import br.com.fullcycle.domain.person.Email;

import java.util.Optional;

public interface PartnerRepository {
    Optional<Partner> partnerOfId(PartnerId anId);

    Optional<Partner> partnerOfCNPJ(CNPJ cnpj);

    Optional<Partner> partnerOfEmail(Email email);

    Partner create(Partner partner);

    Partner update(Partner partner);

    void deleteAll();
}
