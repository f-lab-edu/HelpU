package com.flab.helpu.domain.user.exception;

public class NoSuchUserException extends RuntimeException {

  public NoSuchUserException(String message) {
    super(message);
  }
}
