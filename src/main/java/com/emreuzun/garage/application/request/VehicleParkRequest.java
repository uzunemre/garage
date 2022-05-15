package com.emreuzun.garage.application.request;

import com.emreuzun.garage.domain.model.VehicleColor;
import com.emreuzun.garage.domain.model.VehicleType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VehicleParkRequest {

    @NotNull
    private String plaque;

    @NotNull
    private VehicleColor color;

    @NotNull
    private VehicleType type;


}
