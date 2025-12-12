package com.tickatch.logservice.log.domain.exception;

import com.tickatch.logservice.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LogErrorCode implements ErrorCode {
  LOG_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "LOG_NOT_FOUND");

  private final int status;
  private final String code;
}
