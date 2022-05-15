package com.emreuzun.garage.domain.factory;

import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;

import java.util.*;

public class TicketFactory {

    public static Ticket createTicket(List<Slot> slots, Vehicle vehicle) {
        String id = UUID.randomUUID().toString();
        Integer slotNo = slots.stream()
                .map(Slot::getNo)
                .mapToInt(s -> s)
                .min()
                .orElseThrow(NoSuchElementException::new);

        return Ticket.builder()
                .id(id)
                .slotNo(slotNo)
                .slots(new HashSet<>(slots))
                .vehicle(vehicle)
                .active(true)
                .build();
    }

}
