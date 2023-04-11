package com.flab.helpu.global.exception;

import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 *@ControllerAdvice : @Controller나 @RestController에서 발생한 예외를 한 곳에서 관리하고 처리할 수 있게
 *                    도와주는 어노테이션이다.
 *@Slf4j : SLF4J는 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음이다.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicatedValueException.class)
  public ResponseEntity<HttpStatus> handleDuplicateEmailExistsException(
      DuplicatedValueException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

}
