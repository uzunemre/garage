package com.emreuzun.garage.infrastracture.configuration;

import com.emreuzun.garage.GarageApplication;
import com.emreuzun.garage.domain.repository.SlotRepository;
import com.emreuzun.garage.domain.repository.TicketRepository;
import com.emreuzun.garage.domain.service.DomainSlotService;
import com.emreuzun.garage.domain.service.DomainTicketService;
import com.emreuzun.garage.domain.service.SlotService;
import com.emreuzun.garage.domain.service.TicketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = GarageApplication.class)
public class BeanConfiguration {

    @Bean
    SlotService slotService(final SlotRepository slotRepository, final DomainTicketService domainTicketService) {
        return new DomainSlotService(slotRepository, domainTicketService);
    }

    @Bean
    TicketService ticketService(final TicketRepository ticketRepository){
        return new DomainTicketService(ticketRepository);
    }
}
