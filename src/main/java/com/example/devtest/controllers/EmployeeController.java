package com.example.devtest.controllers;

import com.example.devtest.beans.Page;
import com.example.devtest.models.Employee;
import com.example.devtest.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;


@RestController
@RequestMapping("/employees")
public class EmployeeController extends AbstractPagedController<Employee> {

    private final EmployeeRepository employeeRepository;
    private Date today;
    @Value("${controllers.employee.pageSize:10}")
    private String pageSize;

    /**
     * Custom, catcheable exception used to throw 404 errors
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    static class EmployeeNotFoundException extends RuntimeException {}

    /**
     * Constructs the controller
     *
     * @param employeeRepository the repo for data access
     */
    public EmployeeController(EmployeeRepository employeeRepository) {
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
     * Return listing of available employees.
     * It supports simple paging, with a fixed pagesize {@link .pageSize}, simple search on name and surname,
     * and showing only active employees (the default) or all users
     *
     * @param page which page to show (0-counted)
     * @param showAll whether to show all employees (true) or only active ones
     * @param search string to search for in name and surname
     * @return the paged response
     */
    @GetMapping("/")
    public Page<Employee> listEmployees(@RequestParam(name = "page", defaultValue = "0") String page,
                                        @RequestParam(name = "showAll", defaultValue = "false") String showAll,
                                        @RequestParam(name = "search", defaultValue = "") String search) {
        // Obtain a page request
        Pageable pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(pageSize));
        if(showAll.equalsIgnoreCase("true")) {
            // If we're showing all employees, use the non-date dependent methods in repository (depending if we have a search parameter)
            if(search.length() > 0) {
                return getPage(employeeRepository.findByNameContainingOrSurnameContaining(search, search, pageRequest));
            }
            else {
                return getPage(employeeRepository.findAll(pageRequest));
            }
        }
        else {
            // Use methods for filtering out only active employees, using length of search to drive repository method to use
            if(search.length() > 0) {
                return getPage(
                        employeeRepository.searchAllActive(search, getToday(), pageRequest.getPageSize(), pageRequest.getOffset()),
                        employeeRepository.searchAllActiveCount(search, getToday()),
                        pageRequest);
            }
            else {
                return getPage(
                        employeeRepository.findAllActive(getToday(), pageRequest.getPageSize(), pageRequest.getOffset()),
                        employeeRepository.count(),
                        pageRequest);
            }
        }
    }

    /**
     * Create a new employee
     *
     * @param employee the employee itself, in JSON format, in the request body (minus the id)
     * @return The generated employee (with id)
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Retrieves a specific employee
     *
     * @param id The id of the employee to retrieve
     * @return The employee if found, 404 otherwise
     */
    @GetMapping("/{id:[0-9]+}")
    public Employee getEmployee(@PathVariable("id") String id) {
        return employeeRepository.findById(Long.valueOf(id)).orElseThrow(EmployeeNotFoundException::new);
    }

    /**
     * Modify an existing employee.
     *
     * @apiNote The id is immutable
     *
     * @param employee The employee data (full) to overwrite, minus the id, in JSON format, in the request body
     * @param id The id of the employee to modify
     * @return The updated employee, or 404 if not found
     */
    @PutMapping("/{id:[0-9]+}")
    public Employee modifyEmployee(@RequestBody Employee employee, @PathVariable("id") String id) {
        // Checks if employee actually exists, else throw 404
        if(!employeeRepository.existsById(Long.parseLong(id)))
            throw new EmployeeNotFoundException();
        // Set the id to the one provided in path, so it's really immutable even if request body has it
        employee.setId(Long.parseLong(id));
        // Saves
        return employeeRepository.save(employee);
    }

    /**
     * Delete an employee
     *
     * @apiNote This is a <b>hard</b> delete
     *
     * @param id The id of the employee to delete
     */
    @DeleteMapping("/{id:[0-9]+}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteEmployee(@PathVariable("id") String id) {
        if(!employeeRepository.existsById(Long.parseLong(id)))
            throw new EmployeeNotFoundException();
        employeeRepository.deleteById(Long.parseLong(id));
    }
}
