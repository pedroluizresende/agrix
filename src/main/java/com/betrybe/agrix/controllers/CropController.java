package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FertilizerDto;
import com.betrybe.agrix.controllers.dto.MessageDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.CropService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Camada de controle da rota Crops.
 */
@Controller
@RequestMapping("/crops")
public class CropController {

  private final CropService cropService;

  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }


  /**
   * Mapeamento da rota GET "/crops".
   *
   * @return status 200 e lista de plantações
   */
  @GetMapping()
  @Secured({"MANAGER", "ADMIN"})
  public ResponseEntity<List<CropDto>> getAllCrops() {
    List<Crop> allCrops = cropService.getAllCrops();
    List<CropDto> cropDtoList = allCrops.stream()
        .map(crop -> new CropDto(
            crop.getId(),
            crop.getName(),
            crop.getPlantedArea(),
            crop.getFarm().getId(),
            crop.getPlantedDate(),
            crop.getHarvestDate()
        ))
        .toList();
    return ResponseEntity.ok(cropDtoList);
  }

  /**
   * Mapeamento da rota GET "/crops/{id}.
   *
   * @return status 200 e plantação
   */
  @GetMapping("/{id}")
  public ResponseEntity<CropDto> getCropById(@PathVariable Long id) {
    Crop crop = cropService.getCropById(id);
    CropDto cropDto = new CropDto(
        crop.getId(),
        crop.getName(),
        crop.getPlantedArea(),
        crop.getFarm().getId(),
        crop.getPlantedDate(),
        crop.getHarvestDate()
    );
    return ResponseEntity.ok(cropDto);
  }

  /**
   * Mapeamento da rota GET "/crops/search".
   *
   * @param start data de inicio.
   * @param end   data final.
   * @return retorna lista de plantações dentro do periodo informado
   */
  @GetMapping("/search")
  public ResponseEntity<List<CropDto>> getCropByHarvestDate(
      @RequestParam(name = "start") LocalDate start,
      @RequestParam(name = "end") LocalDate end
  ) {
    List<Crop> crops = cropService.getCropByHarvestDate(start, end);

    List<CropDto> cropDtos = crops.stream()
        .map(crop -> new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
            crop.getFarm().getId(), crop.getPlantedDate(), crop.getHarvestDate())).toList();

    return ResponseEntity.ok(cropDtos);
  }

  /**
   * mapeamento da rota GET "/{cropId}/fertilizers/{fertilizerId}", reponsavel por associar
   * fertilizante à uma plantação.
   *
   * @param cropId       identificador da plantação.
   * @param fertilizerId identificador do fertilizante.
   * @return status 201 e mensagem de sucesso
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<MessageDto> addFertilizer(@PathVariable Long cropId,
      @PathVariable Long fertilizerId) {
    String message = cropService.addFertilizer(cropId, fertilizerId);

    MessageDto messageDto = new MessageDto(message);

    return ResponseEntity.status(HttpStatus.CREATED).body(messageDto);
  }

  /**
   * Mapeamento da rota GET "/{cropId}/fertilizers", reponsavel, por listar fertilizantes a partir
   * de uma plantação.
   *
   * @param cropId identificador da plantação.
   * @return status 200 e lista de fertilizantes no body
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity<List<FertilizerDto>> getFertilizersByCropId(@PathVariable Long cropId) {
    List<Fertilizer> fertilizers = cropService.getFertilizersByCropId(cropId);

    List<FertilizerDto> fertilizerDtos = fertilizers.stream()
        .map(fertilizer -> new FertilizerDto(
            fertilizer.getId(),
            fertilizer.getName(),
            fertilizer.getBrand(),
            fertilizer.getComposition()
        )).toList();
    return ResponseEntity.ok(fertilizerDtos);

  }
}
