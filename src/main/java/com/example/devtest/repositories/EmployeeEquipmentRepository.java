package com.example.devtest.repositories;

import com.example.devtest.models.EmployeeEquipment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Collection;

public interface EmployeeEquipmentRepository extends CrudRepository<EmployeeEquipment, Long> {
    /**
     * Finds all employee-equipment relations active as of today, for a given equipment
     *
     * @param equipment The equipment we want to filter for
     * @param today The date of today
     * @return Collection of found employee equipments
     */
    @Query(name = "EmployeeEquipmentRepository.findAllByEquipmentActive")
    Collection<EmployeeEquipment> findAllByEquipmentActive(@Param("equipment") Long equipment, @Param("today") Date today);

    /**
     * Finds all employee-equipment relations for a given equipment
     *
     * @param equipment The equipment we want to filter for
     * @return Collection of found employee equipments
     */
    @Query(name = "EmployeeEquipmentRepository.findAllByEquipment")
    Collection<EmployeeEquipment> findAllByEquipment(@Param("equipment") Long equipment);

    /**
     * Finds all employee-equipment relations active as of today, for a given employee
     *
     * @param employee The employee we want to filter for
     * @param today The date of today
     * @return Collection of found employee equipments
     */
    @Query(name = "EmployeeEquipmentRepository.findAllByEmployeeActive")
    Collection<EmployeeEquipment> findAllByEmployeeActive(@Param("employee") Long employee, @Param("today") Date today);

    /**
     * Finds all employee-equipment relations for a given employee
     *
     * @param employee The employee we want to filter for
     * @return Collection of found employee equipments
     */
    @Query(name = "EmployeeEquipmentRepository.findAllByEmployee")
    Collection<EmployeeEquipment> findAllByEmployee(@Param("employee") Long employee);
}
