package com.flab.helpu.domain.user.dto;

import com.flab.helpu.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserResonse {

  private Long idx;
  private String userId;
  private String nickname;
  private String email;
  private String userPhoneNumber;

  public static LoginUserResonse of(User user) {
    return LoginUserResonse.builder()
        .idx(user.getIdx())
        .userId(user.getUserId())
        .nickname(user.getNickname())
        .email(user.getEmail())
        .userPhoneNumber(user.getUserPhoneNumber())
        .build();

  }

}
