package com.betrybe.agrix.models.repositories;

import com.betrybe.agrix.models.entities.Crop;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositório de plantação.
 */
@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
  @Query(
      value = "SELECT c FROM Crop c WHERE c.harvestDate BETWEEN :start AND :end"
  )
  List<Crop> findAllByHarvestDateBetween(LocalDate start, LocalDate end);
}
