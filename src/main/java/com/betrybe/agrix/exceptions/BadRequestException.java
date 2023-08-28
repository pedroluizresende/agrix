package com.betrybe.agrix.exceptions;

/**
 * Classe BadRequestExcepetion.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}
