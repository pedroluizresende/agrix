package com.betrybe.agrix.services;

import com.betrybe.agrix.exceptions.NotFoundException;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FarmRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço de Farm.
 */
@Service
public class FarmService {

  private final FarmRepository farmRepository;

  private final CropRepository cropRepository;

  /**
   * Método construtor da camada, com injeção de dependência.
   *
   * @param farmRepository repositório de Fazenda.
   * @param cropRepository repositório de Plantação.
   */
  @Autowired
  public FarmService(FarmRepository farmRepository, CropRepository cropRepository) {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  public Farm insertFarm(Farm newFarm) {
    return farmRepository.save(newFarm);
  }

  /**
   * Método responsavel por buscar todas as fazendas no banco de dados.
   *
   * @return lista de fazendas
   */
  public List<Farm> getAllFarms() {
    return farmRepository.findAll();
  }

  /**
   * Método reponsável por buscar fazenda pelo ID no bando de dados.
   *
   * @param id do tipo Long, representa identificador da fazenda no banco.
   * @return Uma fazenda ou lança um erro.
   */
  public Farm getFarmById(Long id) {
    Optional<Farm> optionalFarm = farmRepository.findById(id);

    if (optionalFarm.isEmpty()) {
      throw new NotFoundException("Fazenda não encontrada!");
    }
    return optionalFarm.get();
  }

  /**
   * Método que adiciona uma plantação á uma fazenda.
   */
  public Crop insertCrop(Long farmId, Crop crop) {
    Farm farm = this.getFarmById(farmId);
    crop.setFarm(farm);

    Crop cropFromDb = cropRepository.save(crop);
    farm.getCrops().add(cropFromDb);

    this.insertFarm(farm);

    return cropFromDb;

  }

  public List<Crop> getCropsByFarmId(Long farmId) {
    Farm farm = this.getFarmById(farmId);
    return farm.getCrops();
  }
}
