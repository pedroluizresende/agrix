package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.AuthenticationDto;
import com.betrybe.agrix.controllers.dto.TokenDto;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.services.PersonService;
import com.betrybe.agrix.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Mapeamento da rota "/auth".
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final TokenService tokenService;

  private final PersonService personService;

  /**
   * Método construtor.
   */
  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      TokenService tokenService, PersonService personService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
    this.personService = personService;
  }

  /**
   * Mapeamento da rota POST "/auth/login", responsável por realizar authenticação de usuário.
   *
   * @param authenticationDto corpo da requisição.
   * @return status 200 e token gerado pelo login
   */
  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody AuthenticationDto authenticationDto) {
    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(authenticationDto.username(),
            authenticationDto.password());
    Authentication auth = authenticationManager.authenticate(usernamePassword);
    Person person = (Person) auth.getPrincipal();
    String token = tokenService.generateToken(person);

    TokenDto tokenDto = new TokenDto(token);

    return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
  }
}
