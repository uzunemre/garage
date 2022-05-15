package com.emreuzun.garage.application.rest;

import com.emreuzun.garage.application.request.VehicleParkRequest;
import com.emreuzun.garage.domain.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class VehicleController {

    private final SlotService slotService;

    @Autowired
    public VehicleController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/park")
    ResponseEntity<?> park(@Valid @RequestBody VehicleParkRequest vehicle) {
        slotService.park(vehicle);
        String message = "Allocated " + vehicle.getType().getSlotSize() + " slot";
        return ResponseEntity.ok(message);
    }

    @PostMapping("/leave/{order}")
    ResponseEntity<?> leave(@PathVariable Integer order) {
        slotService.leave(order);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    ResponseEntity<?> park() {
        String status = slotService.getStatus();
        return ResponseEntity.ok(status);
    }

}
