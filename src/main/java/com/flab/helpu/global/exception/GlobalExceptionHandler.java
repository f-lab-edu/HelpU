package com.flab.helpu.global.exception;

import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import com.flab.helpu.domain.user.exception.InvalidPasswordException;
import com.flab.helpu.domain.user.exception.NoSuchUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * @ControllerAdvice : @Controller나 @RestController에서 발생한 예외를 한 곳에서 관리하고 처리할 수 있게
 *                    도와주는 어노테이션이다.
 * @Slf4j : SLF4J는 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음이다.
 * @ExceptionHandler: @Controller Bean내에서 발생하는 특정 예외에 대한 전역적인 예외 처리를 할 수 있게 한다.
 * 동작 과정은 다음과 같다.
 * 1. 디스패처 서블릿이 에러를 catch르 합니다.
 * 2. 디스 패처 서블릿은 다양한 예외 처리기를 가지고 있고,
 *    해당 에러를 처리할 수 있는 HandlerExceptionResolver가 에러를 처리.
 * 3. 전역적인 ControllerAdvice가 처리할 지, 지역적인 Controller 에서 처리할지 검사.
 * 4. ControllerAdvice의 ExceptionHandler로 처리가 가능한지 검사.
 * 5. ControllerAdvice의 ExceptionHandler 메소드를 invoke 하여 예외를 반환한다. 이 때 리플렉션 API를 이용해서
 *    ExceptionHandler의 구현 메소드를 호출해 처리한 에러를 반환하게 된다.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicatedValueException.class)
  public ResponseEntity<HttpStatus> handleDuplicateEmailExistsException(
      DuplicatedValueException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(NoSuchUserException.class)
  public ResponseEntity<Void> handleNoSuchUserException(
      NoSuchUserException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<Void> handlePasswordNotMatchException(
      InvalidPasswordException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }


}
