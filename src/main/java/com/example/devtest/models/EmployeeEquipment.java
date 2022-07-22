package com.example.devtest.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.sql.Date;

@Data
@AccessType(AccessType.Type.PROPERTY)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EmployeeEquipment {
    private @Id Long id;
    private AggregateReference<Employee, Long> employee;
    private AggregateReference<Equipment, Long> equipment;
    private Date assignedFrom;
    private Date assignedTo;
}
