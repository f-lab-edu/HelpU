package com.flab.helpu.domain.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.helpu.domain.user.User;
import com.flab.helpu.domain.user.dao.UserMapper;
import com.flab.helpu.domain.user.dto.CreateUserRequest;
import com.flab.helpu.domain.user.dto.CreateUserResponse;
import com.flab.helpu.domain.user.dto.LoginUserRequest;
import com.flab.helpu.domain.user.dto.LoginUserResonse;
import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import com.flab.helpu.domain.user.exception.InvalidPasswordException;
import com.flab.helpu.domain.user.exception.NoSuchUserException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserMapper userMapper;

  @Spy
  private BCryptPasswordEncoder passwordEncoder;

  @Test
  @DisplayName("회원가입 성공")
  void createUser() {
    CreateUserRequest testRequest = request();
    User testUser = user(testRequest);

    doNothing().when(userMapper).insertUser(any(User.class));
    when(userMapper.findUserByUserId(any(String.class))).thenAnswer(new Answer() {
      private int count = 0;

      public Object answer(InvocationOnMock invocation) {
        if (++count == 1) {
          return Optional.empty();
        } else {
          return Optional.of(testUser);
        }
      }
    });

    CreateUserResponse response = userService.createUser(testRequest);

    Assertions.assertEquals(response.getIdx(), 1L);
    Assertions.assertEquals(testRequest.getUserId(), response.getUserId());
    verify(userMapper).insertUser(any(User.class));
    verify(userMapper, times(2)).findUserByUserId(any(String.class));
  }

  @Test
  @DisplayName("중복 아이디 확인")
  void validateDuplicatedUserId() {
    CreateUserRequest testRequest = CreateUserRequest.builder().userId("test").password("qwer123!")
        .nickname("테스트").email("test@test.com").userPhoneNumber("010-0000-0000")
        .build();

    User testUser = user(testRequest);

    when(userMapper.findUserByUserId(any(String.class))).thenReturn(Optional.of(testUser));

    Assertions.assertThrows(DuplicatedValueException.class,
        () -> userService.createUser(testRequest));
  }

  @Test
  @DisplayName("중복 닉네임 확인")
  void validateDuplicatedNickname() {
    CreateUserRequest testRequest = CreateUserRequest.builder().userId("test3").password("qwer123!")
        .nickname("테스트").email("test@test.com").userPhoneNumber("010-0000-0000")
        .build();

    User testUser = user(testRequest);

    when(userMapper.findUserByNickname(any(String.class))).thenReturn(Optional.of(testUser));

    Assertions.assertThrows(DuplicatedValueException.class,
        () -> userService.createUser(testRequest));
  }

  @Test
  @DisplayName("로그인 성공")
  void successLogin() {
    LoginUserRequest loginRequest = LoginUserRequest.builder()
        .userId("test")
        .password("qwer123!")
        .build();
    User testUser = user(request());

    when(userMapper.findUserByUserId(any(String.class))).thenReturn(Optional.of(testUser));

    LoginUserResonse loginResponse = userService.loginUser(loginRequest);

    Assertions.assertEquals(testUser.getUserId(), loginResponse.getUserId());
    Assertions.assertEquals(testUser.getEmail(), loginResponse.getEmail());
    Assertions.assertEquals(testUser.getNickname(), loginResponse.getNickname());

    verify(userMapper).findUserByUserId(any(String.class));

  }

  @Test
  @DisplayName("로그인 실패 - 아이디 없음")
  void failLoginNoSuchUser() {
    LoginUserRequest loginRequest = LoginUserRequest.builder()
        .userId("test1")
        .password("qwer123!")
        .build();

    User testUser = user(request());

    when(userMapper.findUserByUserId(any(String.class))).thenReturn(Optional.empty());

    Assertions.assertThrows(NoSuchUserException.class, () -> userService.loginUser(loginRequest));

  }

  @Test
  @DisplayName("로그인 실패 - 비밀번호 틀림")
  void failLoginInvalidPassword() {
    LoginUserRequest loginRequest = LoginUserRequest.builder()
        .userId("test")
        .password("qwer1234")
        .build();

    User testUser = user(request());

    when(userMapper.findUserByUserId(any(String.class))).thenReturn(Optional.of(testUser));

    Assertions.assertThrows(InvalidPasswordException.class,
        () -> userService.loginUser(loginRequest));

  }

  private User user(CreateUserRequest createUserRequest) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encryptedPassword = encoder.encode(createUserRequest.getPassword());

    return User.builder()
        .idx(1L)
        .userId(createUserRequest.getUserId())
        .password(encryptedPassword)
        .email(createUserRequest.getEmail())
        .nickname(createUserRequest.getNickname())
        .userPhoneNumber(createUserRequest.getUserPhoneNumber())
        .createdAt(LocalDateTime.now())
        .createdBy(createUserRequest.getUserId())
        .updatedAt(LocalDateTime.now())
        .updatedBy(createUserRequest.getUserId())
        .build();
  }

  private CreateUserRequest request() {
    return CreateUserRequest.builder()
        .userId("test")
        .password("qwer123!")
        .nickname("테스트")
        .email("test@test.com")
        .userPhoneNumber("010-0000-0000")
        .build();
  }

}
