package com.emreuzun.garage.domain.factory;

import com.emreuzun.garage.application.request.VehicleParkRequest;
import com.emreuzun.garage.domain.model.Vehicle;

import java.util.UUID;

public class VehicleFactory {

    public static Vehicle createVehicle(VehicleParkRequest vehicleParkRequest) {
        String id = UUID.randomUUID().toString();
        return Vehicle.builder()
                .id(id)
                .color(vehicleParkRequest.getColor())
                .plaque(vehicleParkRequest.getPlaque())
                .type(vehicleParkRequest.getType()).build();
    }

}
