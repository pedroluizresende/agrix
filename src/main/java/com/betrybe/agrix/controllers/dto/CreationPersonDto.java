package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.security.Role;

/**
 * Classe CreationPersonDto.
 */
public record CreationPersonDto(Long id, String username, String password, Role role) {

  public Person toEntity() {
    return new Person(id, username, password, role);
  }
}
