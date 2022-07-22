INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (1, 'Babyface', 'Finster', 'CEO', '2019-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (2, 'Foghorn', 'Leghorn', 'CTO', '2019-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (3, 'Lola', 'Bunny', 'CFO', '2028-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (4, 'Porky', 'Pig', 'CFO', '1996-01-01', '1998-01-01');
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (5, 'Elmer', 'Fudd', 'CFO', '1998-01-02', '2027-12-31');
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (6, 'Wyle E.', 'Coyote', 'QA', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (7, 'Road', 'Runner', 'FE developer', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (8, 'Speedy', 'Gonzales', 'FE developer', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (9, 'Bugs', 'Bunny', 'Team Lead', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (10, 'Daffy', 'Duck', 'BE developer', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (11, 'Tasmanian', 'Devil', 'DEVOP', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (12, 'Yosemite', 'Sam', 'BE developer', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (13, 'Angus', 'McRory', 'BE developer', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (14, 'Sylvester', 'the Cat', 'QA', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (15, 'Tweety', 'the Bird', 'FE developer', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (16, 'Granny', 'Webster', 'BA', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (17, 'Barnyard', 'Dawg', 'BA', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (18, 'Pepe', 'Le Pew', 'PM', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (19, 'Petunia', 'Pig', 'QA', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (20, 'Melissa', 'Duck', 'PM', '1998-01-01', NULL);
INSERT INTO employee (id, name, surname, role, active_since, active_to) VALUES (21, 'Michigan J.', 'Frog', 'Sales', '1998-01-01', NULL);

INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (1, 'MacBook Air 1', 'PC', 'MA00001', '1998-01-01', NULL);
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (2, 'MacBook Air 2', 'PC', 'MA00002', '1998-01-01', '1999-01-01');
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (3, 'MacBook Air 3', 'PC', 'MA00003', '2028-01-01', NULL);
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (4, 'MacBook Air 4', 'PC', 'MA00004', '1998-01-01', NULL);
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (5, 'MacBook Air 5', 'PC', 'MA00005', '1998-01-01', NULL);
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (6, 'iPhone X 1', 'PC', 'IX00001', '1998-01-01', NULL);
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (7, 'iPhone X 2', 'PC', 'IX00002', '1998-01-01', '1999-01-01');
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (8, 'iPhone X 3', 'PC', 'IX00003', '2028-01-01', NULL);
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (9, 'iPhone X 4', 'PC', 'IX00004', '1998-01-01', NULL);
INSERT INTO equipment (id, name, type, serial, commissioned, decommissioned) VALUES (10, 'iPhone X 5', 'PC', 'IX00005', '1998-01-01', NULL);

INSERT INTO employee_equipment (id, employee, equipment, assigned_from, assigned_to) VALUES (1, 10, 2, '1998-01-01', '1999-01-01');
INSERT INTO employee_equipment (id, employee, equipment, assigned_from, assigned_to) VALUES (2, 10, 3, '2028-01-01', NULL);
INSERT INTO employee_equipment (id, employee, equipment, assigned_from, assigned_to) VALUES (3, 10, 1, '1999-01-01', '2027-12-31');
INSERT INTO employee_equipment (id, employee, equipment, assigned_from, assigned_to) VALUES (4, 20, 7, '1998-01-01', '1999-01-01');
INSERT INTO employee_equipment (id, employee, equipment, assigned_from, assigned_to) VALUES (5, 20, 8, '2028-01-01', NULL);
INSERT INTO employee_equipment (id, employee, equipment, assigned_from, assigned_to) VALUES (6, 20, 6, '1999-01-01', '2027-12-31');
INSERT INTO employee_equipment (id, employee, equipment, assigned_from, assigned_to) VALUES (7, 20, 5, '1998-01-01', NULL);