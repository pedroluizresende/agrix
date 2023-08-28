package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Fertilizer;

/**
 * Classe FertilizerDto.
 */
public record FertilizerDto(Long id, String name, String brand, String composition) {

  public Fertilizer toFertilizer() {
    return new Fertilizer(id, name, brand, composition, null);
  }
}
