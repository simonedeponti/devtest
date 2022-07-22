DROP TABLE IF EXISTS employee_equipment;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS equipment;

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    role VARCHAR(64),
    active_since DATE,
    active_to DATE) ENGINE=InnoDB;

CREATE TABLE equipment (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(64),
    serial VARCHAR(255),
    commissioned DATE,
    decommissioned DATE) ENGINE=InnoDB;

CREATE TABLE employee_equipment (
    id SERIAL PRIMARY KEY,
    employee BIGINT UNSIGNED NOT NULL,
    equipment BIGINT UNSIGNED NOT NULL,
    assigned_from DATE,
    assigned_to DATE,
    INDEX (employee),
    INDEX (equipment),
    FOREIGN KEY (employee) REFERENCES employee(id) ON DELETE CASCADE,
    FOREIGN KEY (equipment) REFERENCES equipment(id) ON DELETE CASCADE) ENGINE=InnoDB;