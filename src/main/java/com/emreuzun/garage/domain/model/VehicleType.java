package com.emreuzun.garage.domain.model;

public enum VehicleType {

    CAR(1),
    JEEP(2),
    TRUCK(4);

    private final int slotSize;

    VehicleType(int slotSize) {
        this.slotSize = slotSize;
    }

    public int getSlotSize() {
        return slotSize;
    }
}