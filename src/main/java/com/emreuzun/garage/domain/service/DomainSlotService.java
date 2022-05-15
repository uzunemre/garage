package com.emreuzun.garage.domain.service;

import com.emreuzun.garage.application.request.VehicleParkRequest;
import com.emreuzun.garage.domain.DomainException;
import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.model.Ticket;
import com.emreuzun.garage.domain.model.VehicleType;
import com.emreuzun.garage.domain.repository.SlotRepository;
import com.emreuzun.garage.domain.usecase.CarParkStrategy;
import com.emreuzun.garage.domain.usecase.OtherVehicleParkStrategy;
import com.emreuzun.garage.domain.usecase.ParkStrategy;

import java.util.*;
import java.util.stream.Collectors;


public class DomainSlotService implements SlotService {


    private final SlotRepository slotRepository;

    private final DomainTicketService ticketService;

    private final Integer maxSlot = 10;

    private Map<VehicleType, ParkStrategy> parkStrategyMap;


    public DomainSlotService(SlotRepository slotRepository, DomainTicketService ticketService) {
        this.slotRepository = slotRepository;
        this.ticketService = ticketService;
    }

    @Override
    public void park(VehicleParkRequest vehicleParkRequest) {
        validateParkIsEmpty();
        List<Integer> availableSlots = getSortedAvailableSlotsWithoutNext();
        ParkStrategy parkStrategy = getParkStrategy(vehicleParkRequest.getType());
        if (availableSlots.isEmpty()) {
            availableSlots = getSortedAvailableSlotsWithNext();
            parkStrategy.park(vehicleParkRequest, availableSlots);
        } else {
            parkStrategy.park(vehicleParkRequest, availableSlots);
        }
    }

    @Override
    public String leave(Integer slotNo) {
        Optional<Ticket> optionalTicket = ticketService.findAll().stream()
                .filter(Ticket::isActive)
                .filter(t -> t.getSlotNo().equals(slotNo)).findFirst();
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setActive(false);
            ticketService.update(ticket);
            ticket.getSlots().forEach(slot -> {
                slot.setEmpty(true);
                slotRepository.update(slot);
            });
            return "Allocated " + ticket.getVehicle().getType().getSlotSize() + " slot";
        }
        throw new DomainException("Ticket Not Found");
    }

    @Override
    public String getStatus() {
        Map<Integer, Ticket> ticketSlotMap = new HashMap<>();
        ticketService.findAll().stream().filter(Ticket::isActive).forEach(t -> {
            int slotNo = (t.getSlotsAsInt()
                    .stream()
                    .mapToInt(s -> s)
                    .min()
                    .orElseThrow(NoSuchElementException::new));

            ticketSlotMap.put(slotNo, t);
        });
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Status:");
        stringBuilder.append(System.getProperty("line.separator"));
        ticketSlotMap.keySet().stream().sorted().forEach(k -> {
            Ticket ticket = ticketSlotMap.get(k);
            String output = ticket.getVehicle().getPlaque() + " " + ticket.getVehicle().getColor() + " " + ticket.getSlotsAsInt();
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(output);
        });
        return stringBuilder.toString();
    }

    //getHoldedAndNextSlots
    private List<Integer> getSortedAvailableSlotsWithoutNext() {
        List<Integer> allSlots = slotRepository.findAll()
                .stream()
                .map(Slot::getNo)
                .collect(Collectors.toList());

        List<Integer> unAvailableSlots = getUnAvailableSlots();

        return allSlots.stream()
                .filter(s -> !unAvailableSlots.contains(s))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Integer> getUnAvailableSlots() {
        Set<Integer> slots = new HashSet<>();
        ticketService.findAll().stream().filter(Ticket::isActive).forEach(t -> {
            Set<Integer> holded = t.getSlotsAsInt();
            slots.addAll(holded);
            int nextSlot = (holded
                    .stream()
                    .mapToInt(s -> s)
                    .max()
                    .orElseThrow(NoSuchElementException::new)) + 1;
            if (nextSlot <= maxSlot) {
                slots.add(nextSlot);
            }
        });
        return new ArrayList<>(slots);
    }

    private List<Integer> getSortedAvailableSlotsWithNext() {
        List<Integer> allSlots = slotRepository.findAll()
                .stream()
                .map(Slot::getNo)
                .collect(Collectors.toList());

        Set<Integer> holdedSlots = new HashSet<>();
        ticketService.findAll().stream().filter(Ticket::isActive).forEach(t -> {
            Set<Integer> holded = t.getSlotsAsInt();
            holdedSlots.addAll(holded);
        });

        return allSlots.stream()
                .filter(s -> !holdedSlots.contains(s))
                .sorted()
                .collect(Collectors.toList());
    }

    private void validateParkIsEmpty() {
        List<Slot> slots = slotRepository.findAll();
        boolean isFull = slots.stream().noneMatch(Slot::isEmpty);
        if (isFull) {
            throw new DomainException("Garage is full");
        }
    }

    private ParkStrategy getParkStrategy(VehicleType vehicleType) {
        initCreateGameHandler();
        ParkStrategy gameStrategy = parkStrategyMap.get(vehicleType);
        if (vehicleType == null) {
            throw new DomainException("Park Strategy Not Found");
        }
        return gameStrategy;
    }

    private void initCreateGameHandler() {
        if (parkStrategyMap == null) {
            parkStrategyMap = new HashMap<>();
            parkStrategyMap.put(VehicleType.CAR, new CarParkStrategy(slotRepository, ticketService));
            parkStrategyMap.put(VehicleType.JEEP, new OtherVehicleParkStrategy(slotRepository, ticketService));
            parkStrategyMap.put(VehicleType.TRUCK, new OtherVehicleParkStrategy(slotRepository, ticketService));
        }
    }

}
