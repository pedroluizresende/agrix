package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CreationPersonDto;
import com.betrybe.agrix.controllers.dto.PersonDto;
import com.betrybe.agrix.exceptions.BadRequestException;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.services.PersonService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Camada de controller da rota "/persons".
 */
@Controller
@RequestMapping("persons")
public class PersonController {

  private final PersonService personService;

  private final AuthenticationManager authenticationManager;


  @Autowired
  public PersonController(PersonService personService,
      AuthenticationManager authenticationManager) {
    this.personService = personService;
    this.authenticationManager = authenticationManager;
  }

  /**
   * Mapeamento da rota POST "/persons", reponsável por adicionar uma pessoa ao bando de dados.
   *
   * @param creationPersonDto informações da pessoa via body da requisição.
   * @return status 201 e um objeto de PersonDto
   */
  @PostMapping()
  public ResponseEntity<PersonDto> createPerson(@RequestBody CreationPersonDto creationPersonDto) {
    UserDetails userDetails = personService.loadUserByUsername(creationPersonDto.username());
    if (userDetails != null) {
      throw new BadRequestException("username existente");
    }
    Person person = personService.create(creationPersonDto.toEntity());
    PersonDto personDto = new PersonDto(person.getId(), person.getUsername(), person.getRole());

    return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
  }
}
