package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Mapeamento das rotas "farm".
 */
@Controller
@RequestMapping(value = "/farms")
public class FarmController {

  private final FarmService farmService;

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  /**
   * Mapeamento da rota POST ("/farms") responsável por criar uma nova fazenda.
   */
  @PostMapping()
  public ResponseEntity<FarmDto> createFarm(@RequestBody FarmDto farmDto) {
    Farm newFarm = farmService.insertFarm(farmDto.toFarm());

    FarmDto newFarmDto = new FarmDto(newFarm.getId(), newFarm.getName(), newFarm.getSize());

    return ResponseEntity.status(HttpStatus.CREATED).body(newFarmDto);
  }

  /**
   * Mapeamento da rota GET "/farms" responsável listar todas as fazendas.
   *
   * @return retorna JSON com um array de Fazendas
   */
  @GetMapping()
  public ResponseEntity<List<FarmDto>> getAllFarms() {
    List<Farm> farms = farmService.getAllFarms();
    List<FarmDto> farmDtoList = farms.stream()
        .map(farm -> new FarmDto(farm.getId(), farm.getName(), farm.getSize())).toList();
    return ResponseEntity.ok(farmDtoList);
  }

  /**
   * Mapeamento da rota farms/{id}.
   *
   * @param id representa identificador único da fazenda procurada.
   * @return retorna fazenda com o ID passado e status 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<FarmDto> getFarmById(@PathVariable Long id) {
    Farm farm = farmService.getFarmById(id);
    FarmDto newFarmDto = new FarmDto(farm.getId(), farm.getName(), farm.getSize());

    return ResponseEntity.ok(newFarmDto);
  }

  /**
   * Mapeamento da rota POSt "/farms/{farmId}/crops".
   *
   * @param farmId  representa id da fazenda que será adicionado a plantação.
   * @param cropDto representa plantação a ser adicionada na fazenda.
   * @return retorna fazenda adicionada e status 201
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<CropDto> createCrop(@PathVariable Long farmId,
      @RequestBody CropDto cropDto) {
    Crop crop = farmService.insertCrop(farmId, cropDto.toCrop());
    CropDto cropDtoResponse = new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
        farmId, crop.getPlantedDate(), crop.getHarvestDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(cropDtoResponse);
  }

  /**
   * Mapeamento da rota GET "/farms/{farmId}/crops".
   *
   * @param farmId representa identificador da fazenda.
   * @return status 200 e uma lista de plantações
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity<List<CropDto>> getCropsByFarmId(@PathVariable Long farmId) {
    List<Crop> crops = farmService.getCropsByFarmId(farmId);
    List<CropDto> cropDtoList = crops.stream()
        .map(crop -> new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(), farmId,
            crop.getPlantedDate(), crop.getHarvestDate()))
        .toList();
    return ResponseEntity.ok(cropDtoList);
  }
}
