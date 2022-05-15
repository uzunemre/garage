package com.emreuzun.garage.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class Ticket {

    private String id;

    private Vehicle vehicle;

    private Set<Slot> slots = new HashSet<>();

    private boolean active;

    private Integer order ;

    public Set<Integer> getSlotsAsInt() {
        return slots.stream().map(Slot::getNo).collect(Collectors.toSet());
    }

}
