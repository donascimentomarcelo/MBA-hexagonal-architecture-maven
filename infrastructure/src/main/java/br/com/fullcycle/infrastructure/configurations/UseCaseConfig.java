package br.com.fullcycle.infrastructure.configurations;

import br.com.fullcycle.application.customer.CreateCustomerUseCase;
import br.com.fullcycle.application.customer.GetCustomerByIdUseCase;
import br.com.fullcycle.application.event.CreateEventUseCase;
import br.com.fullcycle.application.event.SubscribeCustomerToEventUseCase;
import br.com.fullcycle.application.partner.CreatePartnerUseCase;
import br.com.fullcycle.application.partner.GetPartnerByIdUseCase;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.domain.partner.PartnerRepository;
import br.com.fullcycle.domain.ticket.TicketRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    public final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final PartnerRepository partnerRepository;
    private final TicketRepository ticketRepository;

    public UseCaseConfig(final CustomerRepository customerRepository,
                         final EventRepository eventRepository,
                         final PartnerRepository partnerRepository,
                         final TicketRepository ticketRepository) {
        this.customerRepository = customerRepository;
        this.eventRepository = eventRepository;
        this.partnerRepository = partnerRepository;
        this.ticketRepository = ticketRepository;
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new CreateCustomerUseCase(customerRepository);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        return new CreateEventUseCase(eventRepository, partnerRepository);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(partnerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(customerRepository);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        return new GetPartnerByIdUseCase(partnerRepository);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
    }
}
