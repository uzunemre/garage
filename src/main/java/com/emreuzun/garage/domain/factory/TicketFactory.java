package com.emreuzun.garage.domain.factory;

import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;

import java.util.*;

public class TicketFactory {

    public static Ticket createTicket(List<Slot> slots, Vehicle vehicle, Integer order) {
        String id = UUID.randomUUID().toString();
        return Ticket.builder()
                .id(id)
                .order(order)
                .slots(new HashSet<>(slots))
                .vehicle(vehicle)
                .active(true)
                .build();
    }

}
