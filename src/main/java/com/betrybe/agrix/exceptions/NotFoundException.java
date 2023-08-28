package com.betrybe.agrix.exceptions;

/**
 * Exceção personalizada para NOT_FOUND.
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
}
