package com.example.devtest.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Data
@AccessType(AccessType.Type.PROPERTY)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Equipment {
    private @Id Long id;
    private String name;
    private String type;
    private String serial;
    private Date commissioned;
    private Date decommissioned;
}
