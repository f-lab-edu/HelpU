package com.flab.helpu.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {

  @NotBlank(message = "아이디 값이 없습니다.") //Null, "", " "(공백)이면 에러를 발생시켜주는 어노테이션입니다.
  private String userId;

  @NotBlank(message = "비밀번호 값이 없습니다.")
  private String password;

  @NotBlank(message = "닉네임 값이 없습니다.")
  private String nickname;

  @NotBlank
  @Email(regexp = "^[A-Za-z][A-Za-z0-9.-_]+@[A-Za-z0-9.-_]+.[A-Za-z]+",
      message = "이메일 형식에 맞지 않습니다")
  private String email;

  @NotBlank(message = "휴대전화 번호 값이 없습니다.")
  private String userPhoneNumber;


}
