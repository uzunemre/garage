package com.emreuzun.garage.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {

    private String id;

    private String plaque;

    private VehicleColor color;

    private VehicleType type;
}
