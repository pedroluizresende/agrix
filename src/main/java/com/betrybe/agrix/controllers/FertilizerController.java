package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.controllers.dto.FertilizerDto;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.FertilizerService;
import java.util.List;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Camada de controller da rota "/fertilizers".
 */
@Controller
@RequestMapping("/fertilizers")
public class FertilizerController {

  private final FertilizerService fertilizerService;

  @Autowired

  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * Mapeamento da rota POST "/fertilizers" responsável por criar um novo fertilizante.
   *
   * @param fertilizerDto via body da requisição.
   * @return JSON com o novo fertilizante e status 201
   */
  @PostMapping()
  public ResponseEntity<FertilizerDto> createFertilizer(@RequestBody FertilizerDto fertilizerDto) {
    Fertilizer fertilizer = fertilizerService.create(fertilizerDto.toFertilizer());

    FertilizerDto dto = new FertilizerDto(fertilizer.getId(), fertilizer.getName(),
        fertilizer.getBrand(), fertilizer.getComposition());
    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
  }

  /**
   * Mapeamento da rota GET "/fertilizers" responsável por listar todos os fertilizantes.
   *
   * @return status 200 e lista de fertilizantes
   */
  @GetMapping()
  @Secured("ADMIN")
  public ResponseEntity<List<FertilizerDto>> getAllFertilizers() {
    List<Fertilizer> fertilizers = fertilizerService.getAll();

    List<FertilizerDto> fertilizerDtos = fertilizers.stream()
        .map(fertilizer -> new FertilizerDto(fertilizer.getId(), fertilizer.getName(),
            fertilizer.getBrand(), fertilizer.getComposition())).toList();
    return ResponseEntity.ok(fertilizerDtos);
  }

  /**
   * Mapeamento da rota GET "/farms/{ID} reponsável por buscar fertilizante pelo seu id.
   *
   * @param id via PathVariable, identificador do fertilizante.
   * @return status 200 e fertilizante no body
   */
  @GetMapping("/{id}")
  public ResponseEntity<FertilizerDto> getFertilizerById(@PathVariable long id) {
    Fertilizer fertilizer = fertilizerService.getById(id);
    FertilizerDto fertilizerDto = new FertilizerDto(fertilizer.getId(), fertilizer.getName(),
        fertilizer.getBrand(), fertilizer.getComposition());

    return ResponseEntity.ok(fertilizerDto);
  }
}
