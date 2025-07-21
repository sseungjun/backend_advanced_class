package com.core.backend_advanced.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

  public ConflictException() {
    super(HttpStatus.CONFLICT);
  }

  public ConflictException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}
