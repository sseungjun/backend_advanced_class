package com.core.backend_advanced.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

  public ForbiddenException() {
    super(HttpStatus.FORBIDDEN);
  }

  public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
