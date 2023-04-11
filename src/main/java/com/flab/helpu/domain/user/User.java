package com.flab.helpu.domain.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // getter 메서드 자동 생성 어노테이션입니다.
@Builder // 객체에 빌더패턴을 사용할 수 있게 도와주는 어노테이션입니다.
@NoArgsConstructor // 기본 생성자를 만들어 주는 어노테이션입니다.
@AllArgsConstructor // 해당 객체 내에 있는 모든 변수를 인수로 받는 생성자를 만들어 주는 어노테이션입니다.
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
