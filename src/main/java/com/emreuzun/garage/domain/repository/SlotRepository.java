package com.emreuzun.garage.domain.repository;

import com.emreuzun.garage.domain.model.Slot;

import java.util.List;
import java.util.Optional;

public interface SlotRepository {

    List<Slot> findAll();

    void update(Slot slot);

    List<Slot> findByNo(Integer no);

}
