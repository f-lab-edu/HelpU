package com.flab.helpu.domain.user.dto;

import java.time.LocalDateTime;
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
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

}
