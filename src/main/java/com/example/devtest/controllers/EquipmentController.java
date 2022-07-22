package com.example.devtest.controllers;

import com.example.devtest.beans.EquipmentEmployeeAssignment;
import com.example.devtest.beans.Page;
import com.example.devtest.models.Employee;
import com.example.devtest.models.EmployeeEquipment;
import com.example.devtest.models.Equipment;
import com.example.devtest.repositories.EmployeeEquipmentRepository;
import com.example.devtest.repositories.EmployeeRepository;
import com.example.devtest.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/equipments")
public class EquipmentController extends AbstractPagedController<Equipment> {

    private final EquipmentRepository equipmentRepository;
    private final EmployeeEquipmentRepository employeeEquipmentRepository;
    private final EmployeeRepository employeeRepository;
    @Value("${controllers.equipment.pageSize:10}")
    private String pageSize;
    private Date today;

    /**
     * Custom, catcheable exception used to throw 404 errors
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    static class EquipmentNotFoundException extends RuntimeException {}

    /**
     * Constructs the controller
     *  @param equipmentRepository the repo for data access
     * @param employeeEquipmentRepository the employee-equipment repository
     * @param employeeRepository the employee repository
     */
    public EquipmentController(EquipmentRepository equipmentRepository, EmployeeEquipmentRepository employeeEquipmentRepository, EmployeeRepository employeeRepository) {
        this.equipmentRepository = equipmentRepository;
        this.employeeEquipmentRepository = employeeEquipmentRepository;
        this.employeeRepository = employeeRepository;
        this.today = null;
    }

    /**
     * Use for test/mocking, allows to set a fixed "today" and not rely on clock
     *
     * @param today the date to use as "today" for test purposes
     */
    protected void setToday(Date today) {
        this.today = today;
    }

    /**
     * Returns the current date.
     * Note that for testing purposes you can override the returned date via {@link .setToday(Date)}
     * so that it is not taken by clock but has a fixed, defined value
     *
     * @return The current date
     */
    public Date getToday() {
        // If date is set (overridden) use it, else use the current local date
        return Objects.requireNonNullElseGet(today, () -> Date.valueOf(LocalDate.now()));
    }

    /**
     * Return listing of available equipments.
     * It supports simple paging, with a fixed pagesize {@link .pageSize}, simple search on name and serial,
     * and showing only commissioned equipments (the default) or all
     *
     * @param page which page to show (0-counted)
     * @param showAll whether to show all equipments (true) or only active ones
     * @param search string to search for in name and serial
     * @return the paged response
     */
    @GetMapping("/")
    public Page<Equipment> listEquipments(@RequestParam(name = "page", defaultValue = "0") String page,
                                          @RequestParam(name = "showAll", defaultValue = "false") String showAll,
                                          @RequestParam(name = "search", defaultValue = "") String search) {
        // Obtain a page request
        Pageable pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(pageSize));
        if(showAll.equalsIgnoreCase("true")) {
            // If we're showing all equipments, use the non-date dependent methods in repository (depending if we have a search parameter)
            if(search.length() > 0) {
                return getPage(equipmentRepository.findByNameContainingOrSerialContaining(search, search, pageRequest));
            }
            else {
                return getPage(equipmentRepository.findAll(pageRequest));
            }
        }
        else {
            // Use methods for filtering out only commissioned equipments, using length of search to drive repository method to use
            if(search.length() > 0) {
                return getPage(
                        equipmentRepository.searchAllActive(search, getToday(), pageRequest.getPageSize(), pageRequest.getOffset()),
                        equipmentRepository.searchAllActiveCount(search, getToday()),
                        pageRequest);
            }
            else {
                return getPage(
                        equipmentRepository.findAllActive(getToday(), pageRequest.getPageSize(), pageRequest.getOffset()),
                        equipmentRepository.findAllActiveCount(getToday()),
                        pageRequest);
            }
        }
    }

    /**
     * Create a new equipment
     *
     * @param equipment the equipment itself, in JSON format, in the request body (minus the id)
     * @return The generated equipment (with id)
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Equipment createEquipment(@RequestBody Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    /**
     * Retrieves a specific equipment
     *
     * @param id The id of the equipment to retrieve
     * @return The equipment if found, 404 otherwise
     */
    @GetMapping("/{id:[0-9]+}")
    public Equipment getEquipment(@PathVariable("id") String id) {
        return equipmentRepository.findById(Long.valueOf(id)).orElseThrow(EquipmentController.EquipmentNotFoundException::new);
    }

    /**
     * Modify an existing equipment.
     *
     * @apiNote The id is immutable
     *
     * @param equipment The equipment data (full) to overwrite, minus the id, in JSON format, in the request body
     * @param id The id of the equipment to modify
     * @return The updated equipment, or 404 if not found
     */
    @PutMapping("/{id:[0-9]+}")
    public Equipment modifyEquipment(@RequestBody Equipment equipment, @PathVariable("id") String id) {
        // Checks if equipment actually exists, else throw 404
        if(!equipmentRepository.existsById(Long.parseLong(id)))
            throw new EquipmentController.EquipmentNotFoundException();
        // Set the id to the one provided in path, so it's really immutable even if request body has it
        equipment.setId(Long.parseLong(id));
        // Saves
        return equipmentRepository.save(equipment);
    }

    /**
     * Delete an equipment
     *
     * @apiNote This is a <b>hard</b> delete
     *
     * @param id The id of the equipment to delete
     */
    @DeleteMapping("/{id:[0-9]+}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteEquipment(@PathVariable("id") String id) {
        if(!equipmentRepository.existsById(Long.parseLong(id)))
            throw new EquipmentController.EquipmentNotFoundException();
        equipmentRepository.deleteById(Long.parseLong(id));
    }

    /**
     * Gets the assignments of equipment to employee) realted to this equipment.
     *
     * By default, it shows only active assignments: for past or future ones a query string flag is provided
     *
     * @param id The equipment id for which we want to see the assignments
     * @param showAll Whether to see only active assignments (the default) or all
     * @return The list of assignments
     */
    @GetMapping("/{id:[0-9]+}/employeeassignments")
    public List<EquipmentEmployeeAssignment> getEquipmentEmployeeAssignments(@PathVariable("id") String id, @RequestParam(name = "showAll", defaultValue = "false") String showAll) {
        // Parse id and try to get the equipment
        Long equipmentId = Long.parseLong(id);
        Optional<Equipment> maybeEquipment = equipmentRepository.findById(equipmentId);
        // If equipment is not found, oh well, 404
        if(maybeEquipment.isEmpty())
            throw new EquipmentController.EquipmentNotFoundException();
        // Either get all, oronly active, based on query param
        Collection<EmployeeEquipment> employeeEquipments;
        if(showAll.equalsIgnoreCase("true")) {
            employeeEquipments = employeeEquipmentRepository.findAllByEquipment(equipmentId);
        }
        else {
            employeeEquipments = employeeEquipmentRepository.findAllByEquipmentActive(equipmentId, getToday());
        }
        // Get all the mentioned employees, and retrieve them in batch
        Iterable<Employee> mentionedEmployees = employeeRepository.findAllById(
                employeeEquipments.stream().map(i -> i.getEmployee().getId()).collect(Collectors.toSet()));
        // Turn the result into a map
        Map<Long, Employee> mentionedEmployeesMap = StreamSupport.stream(mentionedEmployees.spliterator(), false)
                .collect(Collectors.toMap(Employee::getId, Function.identity()));
        // Go through all retrieved relations and build the bean
        return employeeEquipments.stream().map(employeeEquipment -> {
            // Get the mentioned employee from the map (no need to check existence here)
            Employee mentionedEmployee = mentionedEmployeesMap.get(employeeEquipment.getEmployee().getId());
            // Make the bean
            return new EquipmentEmployeeAssignment(
                    employeeEquipment.getEmployee().getId(),
                    employeeEquipment.getEmployee().getId(),
                    employeeEquipment.getAssignedFrom(),
                    employeeEquipment.getAssignedTo(),
                    String.format("%s (%s)", maybeEquipment.get().getName(), maybeEquipment.get().getSerial()),
                    String.format("%s %s", mentionedEmployee.getName(), mentionedEmployee.getSurname())
            );
        }).collect(Collectors.toList());
    }
}
