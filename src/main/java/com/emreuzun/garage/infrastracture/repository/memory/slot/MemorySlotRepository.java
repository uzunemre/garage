package com.emreuzun.garage.infrastracture.repository.memory.slot;

import com.emreuzun.garage.domain.model.Slot;
import com.emreuzun.garage.domain.repository.SlotRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Component
public class MemorySlotRepository implements SlotRepository {

    private final List<Slot> slots = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            Slot slot = Slot.builder()
                    .no(i)
                    .empty(true)
                    .build();
            slots.add(slot);
        }
    }

    @Override
    public List<Slot> findAll() {
        return Collections.unmodifiableList(slots);
    }

    @Override
    public void update(Slot slot) {
        for (Slot s : slots) {
            if (s.getNo().equals(slot.getNo())) {
                s.setEmpty(slot.isEmpty());
            }
        }
    }


    @Override
    public List<Slot> findByNo(Integer no) {
        return slots.stream()
                .filter(s -> s.getNo().equals(no))
                .collect(Collectors.toList());
    }

}
