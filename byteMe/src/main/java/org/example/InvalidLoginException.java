package org.example;

public class InvalidLoginException extends RuntimeException {
  public InvalidLoginException(String message) {
    super(message);
  }
}
