package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import java.time.LocalDate;

/**
 * Classe DTO para plantação.
 *
 * @param id          identificador único.
 * @param name        nome da plantação.
 * @param plantedArea representa área de plantio.
 * @param farmId      representa ID único da fazenda.
 */
public record CropDto(Long id, String name, Double plantedArea, Long farmId, LocalDate plantedDate,
                      LocalDate harvestDate) {

  public Crop toCrop() {
    return new Crop(id, name, plantedArea, null, plantedDate, harvestDate, null);
  }
}
