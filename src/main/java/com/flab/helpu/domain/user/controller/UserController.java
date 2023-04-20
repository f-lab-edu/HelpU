package com.flab.helpu.domain.user.controller;

import com.flab.helpu.domain.user.dto.CreateUserRequest;
import com.flab.helpu.domain.user.dto.CreateUserResponse;
import com.flab.helpu.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러 클래스 하위 메서드에 @ResponseBody 를 붙이지 않아도 문자열, JSON 등을 전송할 수 있다.
@RequestMapping("/users") //URL 을 컨트롤러의 메서드와 매핑할 때 사용하는 어노테이션이다.
@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 준다.
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<CreateUserResponse> createUser(
      @Validated @RequestBody CreateUserRequest request) {

    CreateUserResponse user = userService.createUser(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(user);

  }
}
