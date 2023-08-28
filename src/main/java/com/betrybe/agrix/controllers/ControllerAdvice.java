package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.ErrorDto;
import com.betrybe.agrix.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice.
 */

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  /**
   * Mapeamento de errors NotFount.
   */
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ErrorDto> handleNotFoundExcepetion(NotFoundException e) {
    ErrorDto errorDto = new ErrorDto(e.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
  }
}
