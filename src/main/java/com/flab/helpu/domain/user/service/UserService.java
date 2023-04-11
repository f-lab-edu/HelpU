package com.flab.helpu.domain.user.service;

import com.flab.helpu.domain.user.User;
import com.flab.helpu.domain.user.dao.UserMapper;
import com.flab.helpu.domain.user.dto.CreateUserRequest;
import com.flab.helpu.domain.user.dto.CreateUserResponse;
import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service //비즈니스 로직을 수행하는 서비스 레이어임을 알려주는 어노테이션이다.
@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 어노테이션이다.
public class UserService {

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public CreateUserResponse createUser(CreateUserRequest request) {
    validateDuplicatedUserId(request.getUserId());
    validateDuplicatedUserNickname(request.getNickname());

    String test = passwordEncoder.encode(request.getPassword());

    User user = User.builder().userId(request.getUserId())
        .password(passwordEncoder.encode(request.getPassword())).email(request.getEmail())
        .nickname(request.getNickname()).userPhoneNumber(request.getUserPhoneNumber())
        .email(request.getEmail())
        .createdBy(request.getUserId()).updatedBy(request.getUserId()).build();

    userMapper.insertUser(user);

    User insertUser = userMapper.findUserByUserId(request.getUserId())
        .orElseThrow(NullPointerException::new);

    return CreateUserResponse.builder().idx(insertUser.getIdx()).userId(insertUser.getUserId())
        .nickname(insertUser.getNickname()).email(insertUser.getEmail())
        .userPhoneNumber(insertUser.getUserPhoneNumber()).createdAt(insertUser.getCreatedAt())
        .createdBy(insertUser.getCreatedBy()).updatedAt(insertUser.getUpdatedAt())
        .updatedBy(insertUser.getUpdatedBy()).build();
  }

  //중복 아이디 확인
  public void validateDuplicatedUserId(String userId) {
    userMapper.findUserByUserId(userId).ifPresent(user -> {
      throw new DuplicatedValueException("중복된 사용자 ID가 존재합니다.");
    });
  }

  //중복 닉네임 확인
  public void validateDuplicatedUserNickname(String nickname) {
    userMapper.findUserByNickname(nickname).ifPresent(user -> {
      throw new DuplicatedValueException("중복된 닉네임이 존재합니다.");
    });
  }

}
