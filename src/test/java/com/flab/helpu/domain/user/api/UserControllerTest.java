package com.flab.helpu.domain.user.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.helpu.domain.user.controller.UserController;
import com.flab.helpu.domain.user.dto.CreateUserRequest;
import com.flab.helpu.domain.user.dto.CreateUserResponse;
import com.flab.helpu.domain.user.dto.LoginUserRequest;
import com.flab.helpu.domain.user.dto.LoginUserResonse;
import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import com.flab.helpu.domain.user.exception.InvalidPasswordException;
import com.flab.helpu.domain.user.exception.NoSuchUserException;
import com.flab.helpu.domain.user.service.UserService;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

/*
 *@WebMvcTest : MVC를 위한 테스트, 컨트롤러가 예상대로 동작하는지 테스트하는데 사용되는 어노테이션이다.
 *service 의존성을 모의 객체로 대신할 수 있어 독립적인 Controller 단위 테스트를 할 수 있다.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @InjectMocks //@Mock 이 붙은 객체를 @InjectMocks 이 붙은 객체에 주입 시킬 수 있다.
  private UserController userController;

  /*@MockBean 은 스프링 컨텍스트에 mock 객체를 등록하게 되고 스프링 컨텍스트에 의해 @Autowired 가 동작할 때 등록된
   *mock 객체를 사용할 수 있도록 동작한다.
   */
  @MockBean
  private UserService userService;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private HttpSession session;

  @DisplayName("회원 가입 성공")
  @Test
  void signUpSuccess() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder().userId("test").nickname("테스트")
        .password("qwer123!")
        .email("test@test.com").userPhoneNumber("010-0000-0000").build();

    String content = objectMapper.writeValueAsString(request);

    CreateUserResponse response = CreateUserResponse.builder().userId(request.getUserId())
        .nickname(request.getNickname()).email(request.getEmail())
        .userPhoneNumber(request.getUserPhoneNumber()).build();
    when(userService.createUser(any(CreateUserRequest.class))).thenReturn(response);

    mockMvc.perform(post("/users")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userId").value(request.getUserId()))
        .andExpect(jsonPath("$.nickname").value(request.getNickname()))
        .andExpect(jsonPath("$.email").value(request.getEmail()))
        .andExpect(jsonPath("$.userPhoneNumber").value(request.getUserPhoneNumber()));
  }

  @Test
  @DisplayName("필수 정보를 입력하지 않으면 회원가입 실패")
  void createUserFailTest() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder().userId("test2").password("qwer123!")
        .email("test@test.com").userPhoneNumber("010-0000-0000").build();

    String content = objectMapper.writeValueAsString(request);

    mockMvc.perform(post("/users")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("중복아이디 입력 시 회원가입 불가")
  void duplicatedUserId() throws Exception {
    //given
    CreateUserRequest request = CreateUserRequest.builder().userId("test").nickname("테스트3")
        .password("qwer123!")
        .email("test@test.com").userPhoneNumber("010-0000-0000").build();

    String content = objectMapper.writeValueAsString(request);

    when(userService.createUser(any(CreateUserRequest.class))).thenThrow(
        DuplicatedValueException.class);
    //when
    mockMvc.perform(post("/users")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());

    // 행위 검증
    verify(userService, times(1)).createUser(any(CreateUserRequest.class));
  }

  @Test
  @DisplayName("중복닉네임 입력 시 회원가입 불가")
  void duplicatedNickname() throws Exception {
    //given
    CreateUserRequest request = CreateUserRequest.builder().userId("test3").nickname("테스트")
        .password("qwer123!")
        .email("test@test.com").userPhoneNumber("010-0000-0000").build();

    String content = objectMapper.writeValueAsString(request);

    when(userService.createUser(any(CreateUserRequest.class))).thenThrow(
        DuplicatedValueException.class);
    //when
    mockMvc.perform(post("/users")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());

    // 행위 검증
    verify(userService, times(1)).createUser(any(CreateUserRequest.class));
  }

  @Test
  @DisplayName("로그인 성공")
  void successLogin() throws Exception {
    LoginUserRequest request = LoginUserRequest.builder().userId("test").password("qwer123!")
        .build();

    String content = objectMapper.writeValueAsString(request);
    MockHttpSession session = new MockHttpSession();

    LoginUserResonse response = LoginUserResonse.builder()
        .idx(1L)
        .userId(request.getUserId())
        .nickname("테스트")
        .email("test@test.com")
        .userPhoneNumber("010-0000-0000")
        .build();

    when(userService.loginUser(any(LoginUserRequest.class))).thenReturn(response);

    mockMvc.perform(post("/users/login")
            .content(content)
            .session(session)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId")
            .value(response.getUserId()))
        .andExpect(jsonPath("$.idx")
            .value(response.getIdx()))
        .andExpect(jsonPath("$.userPhoneNumber")
            .value(response.getUserPhoneNumber()))
        .andExpect(jsonPath("$.nickname")
            .value(response.getNickname()));

    verify(userService, times(1)).loginUser(any(LoginUserRequest.class));

  }

  @Test
  @DisplayName("로그인 실패 - 아이디 없음 ")
  void failLoginNoSuchUser() throws Exception {
    LoginUserRequest request = LoginUserRequest.builder()
        .userId("test1")
        .password("qwer123!")
        .build();

    String content = objectMapper.writeValueAsString(request);

    MockHttpSession session = new MockHttpSession();

    when(userService.loginUser(any(LoginUserRequest.class))).thenThrow(
        NoSuchUserException.class);

    mockMvc.perform(post("/users/login")
            .content(content)
            .session(session)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());

    verify(userService, times(1)).loginUser(any(LoginUserRequest.class));

  }

  @Test
  @DisplayName("로그인 실패 - 비밀번호 틀림")
  void failLoginInvalidPassword() throws Exception {
    LoginUserRequest request = LoginUserRequest.builder().userId("test1").password("qwer123!")
        .build();

    String content = objectMapper.writeValueAsString(request);

    MockHttpSession session = new MockHttpSession();

    when(userService.loginUser(any(LoginUserRequest.class))).thenThrow(
        InvalidPasswordException.class);

    mockMvc.perform(post("/users/login")
            .content(content)
            .session(session)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());

    verify(userService, times(1)).loginUser(any(LoginUserRequest.class));
  }

}
