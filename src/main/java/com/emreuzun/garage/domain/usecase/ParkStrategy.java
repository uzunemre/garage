package com.emreuzun.garage.domain.usecase;

import com.emreuzun.garage.application.request.VehicleParkRequest;

import java.util.List;

public interface ParkStrategy {

    void park(VehicleParkRequest vehicleParkRequest, List<Integer> availableSlots);

}
