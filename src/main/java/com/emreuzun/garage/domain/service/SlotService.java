package com.emreuzun.garage.domain.service;

import com.emreuzun.garage.application.request.VehicleParkRequest;

public interface SlotService {

    void park(VehicleParkRequest parkRequest);

    String leave(Integer slotNo);

    String getStatus();

}
