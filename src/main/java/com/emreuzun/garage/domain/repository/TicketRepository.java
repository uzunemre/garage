package com.emreuzun.garage.domain.repository;

import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    List<Ticket> findAll();

    void add(Ticket ticket);

    void update(Ticket ticket);

}
