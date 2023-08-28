package com.betrybe.agrix.services;

import com.betrybe.agrix.exceptions.NotFoundException;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FertilizerRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço da rota crops.
 */
@Service
public class CropService {

  private final CropRepository cropRepository;

  private final FertilizerRepository fertilizerRepository;

  @Autowired
  public CropService(CropRepository cropRepository, FertilizerRepository fertilizerRepository) {
    this.cropRepository = cropRepository;
    this.fertilizerRepository = fertilizerRepository;
  }

  /**
   * Método que busca todas as plantações do banco de dados.
   *
   * @return uma lista de plantações
   */
  public List<Crop> getAllCrops() {
    return cropRepository.findAll();
  }

  /**
   * Método que buscar plantação por id.
   *
   * @param id referente ao id da plantação a ser buscada.
   * @return retorna uma plantação
   */
  public Crop getCropById(Long id) {
    Optional<Crop> optionalCrop = cropRepository.findById(id);
    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }
    return optionalCrop.get();
  }

  public List<Crop> getCropByHarvestDate(LocalDate start, LocalDate end) {
    return cropRepository.findAllByHarvestDateBetween(start, end);
  }

  /**
   * Método responsável por associar um fertilizante à uma plantação.
   *
   * @param cropId       identificador da plantação.
   * @param fertilizerId identificador do fertilizante.
   * @return mensagem de sucesso
   */
  @Transactional
  public String addFertilizer(Long cropId, Long fertilizerId) {
    Optional<Crop> optionalCrop = cropRepository.findById(cropId);

    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }
    Optional<Fertilizer> optionalFertilizer = fertilizerRepository.findById(fertilizerId);

    if (optionalFertilizer.isEmpty()) {
      throw new NotFoundException("Fertilizante não encontrado!");
    }

    Crop crop = optionalCrop.get();
    Fertilizer fertilizer = optionalFertilizer.get();

    crop.getFertilizers().add(fertilizer);

    cropRepository.save(crop);

    return "Fertilizante e plantação associados com sucesso!";
  }

  /**
   * método responsável por buscar fertilizantes a partir de uma plantação.
   *
   * @param cropId identificador da platanção.
   * @return lista de fertilizantes
   */
  public List<Fertilizer> getFertilizersByCropId(Long cropId) {
    Optional<Crop> optionalCrop = cropRepository.findById(cropId);

    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }
    return optionalCrop.get().getFertilizers();
  }
}
