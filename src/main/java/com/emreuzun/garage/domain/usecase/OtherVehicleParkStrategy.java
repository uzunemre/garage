package com.emreuzun.garage.domain.usecase;

import com.emreuzun.garage.application.request.VehicleParkRequest;
import com.emreuzun.garage.domain.DomainException;
import com.emreuzun.garage.domain.factory.TicketFactory;
import com.emreuzun.garage.domain.factory.VehicleFactory;
import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.Vehicle;
import com.emreuzun.garage.domain.repository.SlotRepository;
import com.emreuzun.garage.domain.service.DomainTicketService;

import java.util.List;
import java.util.stream.Collectors;

public class OtherVehicleParkStrategy implements ParkStrategy {

    private final SlotRepository slotRepository;

    private final DomainTicketService ticketService;

    public OtherVehicleParkStrategy(SlotRepository slotRepository, DomainTicketService ticketService) {
        this.slotRepository = slotRepository;
        this.ticketService = ticketService;
    }

    @Override
    public void park(VehicleParkRequest vehicleParkRequest, List<Integer> availableSlots) {
        validateParkIsEmpty(vehicleParkRequest, availableSlots);
    }

    private void validateParkIsEmpty(VehicleParkRequest vehicleParkRequest, List<Integer> availableSlots) {
        int vehicleSize = vehicleParkRequest.getType().getSlotSize();
        if (availableSlots.size() < vehicleSize) {
            throw new DomainException("Garage is full");
        }
        List<Slot> closestSlots = getClosestSlots(vehicleParkRequest, availableSlots);
        Vehicle vehicle = VehicleFactory.createVehicle(vehicleParkRequest);
        int order = (int) ticketService.findAll().stream().filter(Ticket::isActive).count() + 1;
        Ticket ticket = TicketFactory.createTicket(closestSlots, vehicle, order);
        ticketService.add(ticket);
        for (Slot slot : closestSlots) {
            slot.setEmpty(false);
            slotRepository.update(slot);
        }
    }

    private List<Slot> getClosestSlots(VehicleParkRequest vehicleParkRequest, List<Integer> availableSlots) {
        int vehicleSize = vehicleParkRequest.getType().getSlotSize();
        int counter = 1;
        for (int i = 1; i < availableSlots.size(); i++) {
            if (availableSlots.get(i) == (availableSlots.get(i - 1) + 1)) {
                counter++;
                if (counter == vehicleSize) {
                    int beginEndex = i - vehicleSize + 1;
                    int endIndex = i + 1;
                    List<Integer> closestSlots = availableSlots.subList(beginEndex, endIndex);
                    return slotRepository.findAll()
                            .stream()
                            .filter(s -> closestSlots.contains(s.getNo())).collect(Collectors.toList());
                }
            } else {
                counter = 1;
            }
        }
        throw new DomainException("Garage is full");
    }

}
