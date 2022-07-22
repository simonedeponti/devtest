package com.example.devtest.beans;

import java.sql.Date;

public class EquipmentEmployeeAssignment {
    private final Long equipmentId;
    private final Long employeeId;
    private final Date assignedFrom;
    private final Date assignedTo;
    private final String equipmentFullName;
    private final String employeeFullName;

    public EquipmentEmployeeAssignment(Long equipmentId, Long employeeId, Date assignedFrom, Date assignedTo, String equipmentFullName, String employeeFullName) {
        this.equipmentId = equipmentId;
        this.employeeId = employeeId;
        this.assignedFrom = assignedFrom;
        this.assignedTo = assignedTo;
        this.equipmentFullName = equipmentFullName;
        this.employeeFullName = employeeFullName;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Date getAssignedFrom() {
        return assignedFrom;
    }

    public Date getAssignedTo() {
        return assignedTo;
    }

    public String getEquipmentFullName() {
        return equipmentFullName;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }
}
