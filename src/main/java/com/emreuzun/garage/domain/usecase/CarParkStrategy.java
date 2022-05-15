package com.emreuzun.garage.domain.usecase;

import com.emreuzun.garage.application.request.VehicleParkRequest;
import com.emreuzun.garage.domain.factory.TicketFactory;
import com.emreuzun.garage.domain.factory.VehicleFactory;
import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;
import com.emreuzun.garage.domain.repository.SlotRepository;
import com.emreuzun.garage.domain.service.DomainTicketService;

import java.util.List;

public class CarParkStrategy implements ParkStrategy {

    private final SlotRepository slotRepository;

    private final DomainTicketService ticketService;

    public CarParkStrategy(SlotRepository slotRepository, DomainTicketService ticketService) {
        this.slotRepository = slotRepository;
        this.ticketService = ticketService;
    }

    @Override
    public void park(VehicleParkRequest vehicleParkRequest, List<Integer> availableSlots) {
        Integer slotNo = availableSlots.get(0);
        List<Slot> closestSlot = slotRepository.findByNo(slotNo);
        Vehicle vehicle = VehicleFactory.createVehicle(vehicleParkRequest);
        int order = (int) ticketService.findAll().stream().filter(Ticket::isActive).count() + 1;
        Ticket ticket = TicketFactory.createTicket(closestSlot, vehicle, order);
        ticketService.add(ticket);
        closestSlot.get(0).setEmpty(false);
        slotRepository.update(closestSlot.get(0));
    }

}
