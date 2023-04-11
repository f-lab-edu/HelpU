package com.flab.helpu.domain.user.service;

import com.flab.helpu.domain.user.dto.CreateUserRequest;
import com.flab.helpu.domain.user.dto.CreateUserResponse;
import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

  /*
   * @Autowired 는 의존성 주입을 할 때 사용하는 Annotation 으로 의존 객체의 타입에 해당하는
   * bean 을 찾아 주입하는 역할을 한다.
   */
  @Autowired
  private UserService userService;

  @Test
  @DisplayName("회원가입")
  @Order(1)
  void createUser() {
    CreateUserRequest request = CreateUserRequest.builder().userId("test").password("qwer123!")
        .nickname("테스트").email("test@test.com").userPhoneNumber("010-0000-0000")
        .build();

    CreateUserResponse response = userService.createUser(request);

    Assertions.assertEquals(response.getIdx(), 1L);
    Assertions.assertEquals(request.getUserId(), response.getUserId());
  }

  @Test
  @DisplayName("중복 아이디 확인")
  @Order(2)
  void validateDuplicatedUserId() {
    CreateUserRequest request = CreateUserRequest.builder().userId("test").password("qwer123!")
        .nickname("테스트").email("test@test.com").userPhoneNumber("010-0000-0000")
        .build();

    Assertions.assertThrows(DuplicatedValueException.class, () -> userService.createUser(request));
  }

  @Test
  @DisplayName("중복 닉네임 확인")
  @Order(3)
  void validateDuplicatedNickname() {
    CreateUserRequest request = CreateUserRequest.builder().userId("test3").password("qwer123!")
        .nickname("테스트").email("test@test.com").userPhoneNumber("010-0000-0000")
        .build();

    Assertions.assertThrows(DuplicatedValueException.class, () -> userService.createUser(request));
  }

}
