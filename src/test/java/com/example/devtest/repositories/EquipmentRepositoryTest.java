package com.example.devtest.repositories;

import com.example.devtest.models.Equipment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EquipmentRepositoryTest {

    @Autowired
    EquipmentRepository repository;

    static Predicate<Equipment> isEquipmentActive(Date today) {
        return equipment -> (equipment.getDecommissioned() == null || equipment.getDecommissioned().after(today)) &&
                (equipment.getCommissioned() == null || equipment.getCommissioned().before(today));
    }

    @Test
    void findAll() {
        Page<Equipment> page1 = repository.findAll(PageRequest.of(0, 10));
        assertEquals(10, page1.getNumberOfElements());
        assertEquals(10, page1.getTotalElements());
        assertEquals(1, page1.getTotalPages());

        Page<Equipment> page2 = repository.findAll(PageRequest.of(1, 10));
        assertEquals(0, page2.getNumberOfElements());
        assertEquals(10, page2.getTotalElements());
        assertEquals(1, page2.getTotalPages());
    }

    @Test
    void findAllByNameContainingOrSerialContaining() {
        Page<Equipment> page1 = repository.findByNameContainingOrSerialContaining("Mac", "Mac", PageRequest.of(0, 10));
        assertEquals(5, page1.getTotalElements());
        assertEquals(1, page1.getTotalPages());
    }

    @Test
    void findAllActive() {
        Date today = Date.valueOf("2022-01-01");
        List<Equipment> equipments1 = repository.findAllActive(today, 10, 0);
        assertEquals(6, equipments1.size());
        assertTrue(equipments1.stream().allMatch(isEquipmentActive(today)));
    }

    @Test
    void searchAllActive() {
        Date today = Date.valueOf("2022-01-01");
        List<Equipment> equipments1 = repository.searchAllActive("Mac", today, 10, 0);
        assertEquals(3, equipments1.size());
        assertTrue(equipments1.stream().allMatch(isEquipmentActive(today)));
    }

    @Test
    void findAllActiveCount() {
        Date today = Date.valueOf("2022-01-01");
        Long count = repository.findAllActiveCount(today);
        assertEquals(6, count);
    }

    @Test
    void searchAllActiveCount() {
        Date today = Date.valueOf("2022-01-01");
        Long count = repository.searchAllActiveCount("Mac", today);
        assertEquals(3, count);
    }
}