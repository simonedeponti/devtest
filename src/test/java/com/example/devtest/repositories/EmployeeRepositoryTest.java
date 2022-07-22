package com.example.devtest.repositories;

import com.example.devtest.models.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository repository;

    static Predicate<Employee> isEmployeeActive(Date today) {
        return employee -> (employee.getActiveTo() == null || employee.getActiveTo().after(today)) &&
            (employee.getActiveSince() == null || employee.getActiveSince().before(today));
    }

    @Test
    void findAll() {
        Page<Employee> page1 = repository.findAll(PageRequest.of(0, 10));
        assertEquals(10, page1.getNumberOfElements());
        assertEquals(21, page1.getTotalElements());
        assertEquals(3, page1.getTotalPages());

        Page<Employee> page2 = repository.findAll(PageRequest.of(2, 10));
        assertEquals(1, page2.getNumberOfElements());
        assertEquals(21, page2.getTotalElements());
        assertEquals(3, page2.getTotalPages());

        Page<Employee> page3 = repository.findAll(PageRequest.of(3, 10));
        assertEquals(0, page3.getNumberOfElements());
        assertEquals(21, page3.getTotalElements());
        assertEquals(3, page3.getTotalPages());
    }

    @Test
    void findByNameContainingOrSurnameContaining() {
        Page<Employee> page1 = repository.findByNameContainingOrSurnameContaining("Bugs", "Bugs", PageRequest.of(0, 10));
        assertEquals(1, page1.getTotalElements());
        assertEquals(1, page1.getTotalPages());
    }

    @Test
    void findAllActive() {
        Date today = Date.valueOf("2022-01-01");
        List<Employee> employees1 = repository.findAllActive(today, 10, 0);
        assertEquals(10, employees1.size());
        assertTrue(employees1.stream().allMatch(isEmployeeActive(today)));

        List<Employee> employees2 = repository.findAllActive(today, 10, 10);
        assertEquals(9, employees2.size());
        assertTrue(employees2.stream().allMatch(isEmployeeActive(today)));

        // Check that paging works, i.e. changing page does not actually returns items already in the other page
        Set<Long> dupIds1 = employees1.stream().map(Employee::getId).collect(Collectors.toSet());
        Set<Long> dupIds2 = employees2.stream().map(Employee::getId).collect(Collectors.toSet());
        dupIds1.retainAll(dupIds2);
        assertEquals(0, dupIds1.size());
    }

    @Test
    void searchAllActive() {
        Date today = Date.valueOf("2022-01-01");
        List<Employee> employees1 = repository.searchAllActive("Bugs", today, 10, 0);
        assertEquals(1, employees1.size());
        assertTrue(employees1.stream().allMatch(isEmployeeActive(today)));
    }

    @Test
    void searchAllActiveCount() {
        Date today = Date.valueOf("2022-01-01");
        Long count = repository.searchAllActiveCount("Bugs", today);
        assertEquals(1, count);
    }
}