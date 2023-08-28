package com.betrybe.agrix.services;

import com.betrybe.agrix.exceptions.NotFoundException;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.FertilizerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço da rota Fertilizer.
 */
@Service
public class FertilizerService {

  private final FertilizerRepository fertilizerRepository;

  @Autowired

  public FertilizerService(FertilizerRepository fertilizerRepository) {
    this.fertilizerRepository = fertilizerRepository;
  }

  /**
   * Método responsável por adicionar um fertilizante no banco.
   *
   * @param fertilizer instancia de Fertilizer.
   * @return objeto adicionado no banco
   */
  public Fertilizer create(Fertilizer fertilizer) {
    return fertilizerRepository.save(fertilizer);
  }

  /**
   * Método responsável por buscar todas os fertilizantes do banco.
   *
   * @return lista de fertilizantes
   */
  public List<Fertilizer> getAll() {
    return fertilizerRepository.findAll();
  }

  /**
   * Método responsável por buscar fertilizante pelo seu id no banco.
   *
   * @param id identificador do fertilizante.
   * @return instancia do objeto Fertilizer
   */
  public Fertilizer getById(Long id) {
    Optional<Fertilizer> optionalFertilizer = fertilizerRepository.findById(id);
    if (optionalFertilizer.isEmpty()) {
      throw new NotFoundException("Fertilizante não encontrado!");
    }
    return optionalFertilizer.get();
  }
}
