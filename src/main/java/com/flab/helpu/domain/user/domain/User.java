package com.flab.helpu.domain.user.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter // getter 메서드 자동 생성 어노테이션입니다.
@Builder // 객체에 빌더패턴을 사용할 수 있게 도와주는 어노테이션입니다.
public class User {

  private Long idx;
  private String userId;
  private String password;
  private String nickname;
  private String email;
  private String userPhoneNumber;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  private LocalDateTime deletedAt;

}
