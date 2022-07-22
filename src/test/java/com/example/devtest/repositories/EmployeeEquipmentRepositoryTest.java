package com.example.devtest.repositories;

import com.example.devtest.models.EmployeeEquipment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmployeeEquipmentRepositoryTest {

    @Autowired
    EmployeeEquipmentRepository repository;

    @Test
    public void testFindById() {
        Optional<EmployeeEquipment> employeeEquipment = repository.findById(1L);

        assertTrue(employeeEquipment.isPresent());
        assertEquals(Date.valueOf("1998-01-01"), employeeEquipment.get().getAssignedFrom());
        assertEquals(2L, employeeEquipment.get().getEquipment().getId());
        assertEquals(10L, employeeEquipment.get().getEmployee().getId());
    }

    @Test
    void findAllByEquipmentActive() {
        Collection<EmployeeEquipment> employeeEquipments1 = repository.findAllByEquipmentActive(5L, Date.valueOf("2022-01-01"));
        assertEquals(1, employeeEquipments1.size());
        assertTrue(employeeEquipments1.stream().allMatch(e -> Objects.equals(5L, e.getEquipment().getId())));

        Collection<EmployeeEquipment> employeeEquipments2 = repository.findAllByEquipmentActive(3L, Date.valueOf("2022-01-01"));
        assertEquals(0, employeeEquipments2.size());

        Collection<EmployeeEquipment> employeeEquipments3 = repository.findAllByEquipmentActive(1L, Date.valueOf("2022-01-01"));
        assertEquals(1, employeeEquipments3.size());
        assertTrue(employeeEquipments3.stream().allMatch(e -> Objects.equals(1L, e.getEquipment().getId())));
    }

    @Test
    void findAllByEquipment() {
        Collection<EmployeeEquipment> employeeEquipments1 = repository.findAllByEquipment(5L);
        assertEquals(1, employeeEquipments1.size());
        assertTrue(employeeEquipments1.stream().allMatch(e -> Objects.equals(5L, e.getEquipment().getId())));

        Collection<EmployeeEquipment> employeeEquipments2 = repository.findAllByEquipment(3L);
        assertEquals(1, employeeEquipments2.size());
        assertTrue(employeeEquipments2.stream().allMatch(e -> Objects.equals(3L, e.getEquipment().getId())));

        Collection<EmployeeEquipment> employeeEquipments3 = repository.findAllByEquipment(1L);
        assertEquals(1, employeeEquipments3.size());
        assertTrue(employeeEquipments3.stream().allMatch(e -> Objects.equals(1L, e.getEquipment().getId())));
    }

    @Test
    void findAllByEmployeeActive() {
        Collection<EmployeeEquipment> employeeEquipments = repository.findAllByEmployeeActive(10L, Date.valueOf("2022-01-01"));
        assertEquals(1, employeeEquipments.size());
        assertTrue(employeeEquipments.stream().allMatch(e -> Objects.equals(10L, e.getEmployee().getId())));
    }

    @Test
    void findAllByEmployee() {
        Collection<EmployeeEquipment> employeeEquipments = repository.findAllByEmployee(10L);
        assertEquals(3, employeeEquipments.size());
        assertTrue(employeeEquipments.stream().allMatch(e -> Objects.equals(10L, e.getEmployee().getId())));
    }
}