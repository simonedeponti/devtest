package com.example.devtest.repositories;

import com.example.devtest.models.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
    /**
     * Get all equipment (paginated)
     *
     * @param pageable The page request
     * @return The requested page
     */
    Page<Equipment> findAll(Pageable pageable);

    /**
     * List equipment whose name or serial contain the specified string
     *
     * @param name The name to search for
     * @param serial The serial to search for
     * @param pageable The page request
     * @return The apged result (requested page only)
     */
    Page<Equipment> findByNameContainingOrSerialContaining(String name, String serial, Pageable pageable);

    /**
     * Find all active equipment
     *
     * @param today The current date
     * @param limit How many items to retrieve (used for pagination)
     * @param offset The offset on db (used for pagination)
     * @return The found elements at the offset and window
     */
    @Query(name = "EquipmentRepository.findAllActive")
    List<Equipment> findAllActive(@Param("today") Date today, @Param("limit") long limit, @Param("offset") long offset);

    /**
     * Search all active equipment for a string contained in name or serial
     *
     * @param q The text to search for in name and serial
     * @param today The current date
     * @param limit How many items to retrieve (used for pagination)
     * @param offset The offset on db (used for pagination)
     * @return The found elements at the offset and window
     */
    @Query(name = "EquipmentRepository.searchAllActive")
    List<Equipment> searchAllActive(@Param("q") String q, @Param("today") Date today, @Param("limit") long limit, @Param("offset") long offset);

    /**
     * Count all active equipment
     *
     * @param today The current date
     * @return The number of matching equipment in database
     */
    @Query(name = "EquipmentRepository.findAllActiveCount")
    Long findAllActiveCount(@Param("today") Date today);

    /**
     * Count all active equipment whose name or serial contain a specific text
     *
     * @param q The text to search for
     * @param today The current date
     * @return The number of matching equipment in database
     */
    @Query(name = "EquipmentRepository.searchAllActiveCount")
    Long searchAllActiveCount(@Param("q") String q, @Param("today") Date today);
}
