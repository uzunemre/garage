package com.emreuzun.garage.infrastracture.repository.memory.ticket;

import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;
import com.emreuzun.garage.domain.repository.TicketRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Primary
@Component
public class MemoryTicketRepository implements TicketRepository {

    private final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<Ticket> findAll() {
        return Collections.unmodifiableList(tickets);
    }

    @Override
    public void add(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++) {
            Ticket savedTicket = tickets.get(i);
            if (savedTicket.getId().equals(ticket.getId())) {
                tickets.set(i, ticket);
            }
        }
    }

}
