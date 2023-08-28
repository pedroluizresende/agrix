package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.security.Role;

/**
 * Classe PersonDto.
 *
 * @param id       identificador da entidade no banco de dados.
 * @param username username da entidade.
 * @param role     role da entidade.
 */
public record PersonDto(Long id, String username, Role role) {

}
