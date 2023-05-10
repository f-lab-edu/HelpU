package com.flab.helpu.domain.user.dto;

import com.flab.helpu.domain.user.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserResponse {

  private Long idx;
  private String userId;
  private String nickname;
  private String email;
  private String userPhoneNumber;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  public static CreateUserResponse of(User user) {
    return CreateUserResponse.builder().idx(user.getIdx()).userId(user.getUserId())
        .nickname(user.getNickname()).email(user.getEmail())
        .userPhoneNumber(user.getUserPhoneNumber()).createdAt(user.getCreatedAt())
        .createdBy(user.getCreatedBy()).updatedAt(user.getUpdatedAt())
        .updatedBy(user.getUpdatedBy()).build();
  }

}
