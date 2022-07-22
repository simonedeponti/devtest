package com.example.devtest.repositories;


import com.example.devtest.models.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    /**
     * Get all employees (paginated)
     *
     * @param pageable The page request
     * @return The requested page
     */
    Page<Employee> findAll(Pageable pageable);

    /**
     * List employees whose name or surname contain the specified string
     *
     * @param name The name to search for
     * @param surname The surname to search for
     * @param pageable The page request
     * @return The apged result (requested page only)
     */
    Page<Employee> findByNameContainingOrSurnameContaining(String name, String surname, Pageable pageable);

    /**
     * Find all active employees
     *
     * @param today The current date
     * @param limit How many items to retrieve (used for pagination)
     * @param offset The offset on db (used for pagination)
     * @return The found elements at the offset and window
     */
    @Query(name = "EmployeeRepository.findAllActive")
    List<Employee> findAllActive(@Param("today") Date today, @Param("limit") long limit, @Param("offset") long offset);

    /**
     * Search all active employees for a string contained in name or surname
     *
     * @param q The text to search for in name and surname
     * @param today The current date
     * @param limit How many items to retrieve (used for pagination)
     * @param offset The offset on db (used for pagination)
     * @return The found elements at the offset and window
     */
    @Query(name = "EmployeeRepository.searchAllActive")
    List<Employee> searchAllActive(@Param("q") String q, @Param("today") Date today, @Param("limit") long limit, @Param("offset") long offset);

    /**
     * Count all active employees whose name or surname contain a specific text
     *
     * @param q The text to search for
     * @param today The current date
     * @return The number of matching employees in database
     */
    @Query(name = "EmployeeRepository.searchAllActiveCount")
    Long searchAllActiveCount(@Param("q") String q, @Param("today") Date today);
}
