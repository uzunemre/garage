package com.emreuzun.garage.domain.service;

import com.emreuzun.garage.domain.DomainException;
import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;
import com.emreuzun.garage.domain.repository.TicketRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Primary
@Component
public class DomainTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public DomainTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public void add(Ticket ticket) {
        boolean carExist = ticketRepository.findAll()
                .stream()
                .filter(Ticket::isActive)
                .anyMatch(t -> t.getVehicle().getPlaque().equals(ticket.getVehicle().getPlaque()));
        if (carExist) {
            throw new DomainException("Vehicle already exist");
        }
        ticketRepository.add(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketRepository.update(ticket);
    }
}
