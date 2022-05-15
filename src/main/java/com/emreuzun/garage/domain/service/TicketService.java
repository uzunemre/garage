package com.emreuzun.garage.domain.service;

import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    List<Ticket> findAll();

    void add(Ticket ticket);

    void update(Ticket ticket);
}
