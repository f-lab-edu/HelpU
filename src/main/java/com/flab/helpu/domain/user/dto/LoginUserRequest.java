package com.flab.helpu.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginUserRequest {

  @NotNull
  private String userId;
  @NotNull
  private String password;

}
