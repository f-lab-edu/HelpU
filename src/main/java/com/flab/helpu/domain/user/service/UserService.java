package com.flab.helpu.domain.user.service;

import com.flab.helpu.domain.user.User;
import com.flab.helpu.domain.user.dao.UserMapper;
import com.flab.helpu.domain.user.dto.CreateUserRequest;
import com.flab.helpu.domain.user.dto.CreateUserResponse;
import com.flab.helpu.domain.user.dto.LoginUserRequest;
import com.flab.helpu.domain.user.dto.LoginUserResonse;
import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import com.flab.helpu.domain.user.exception.InvalidPasswordException;
import com.flab.helpu.domain.user.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //비즈니스 로직을 수행하는 서비스 레이어임을 알려주는 어노테이션이다.
@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 어노테이션이다.
public class UserService {

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional // @Transactional은 클래스나 메서드에 붙여줄 경우, 해당 범위 내 메서드가 트랜잭션이 되도록 보장해준다.
  public CreateUserResponse createUser(CreateUserRequest request) {
    validateDuplicatedUserId(request.getUserId());
    validateDuplicatedUserNickname(request.getNickname());

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
  private void validateDuplicatedUserId(String userId) {
    userMapper.findUserByUserId(userId).ifPresent(user -> {
      throw new DuplicatedValueException("중복된 사용자 ID가 존재합니다.");
    });
  }

  //중복 닉네임 확인
  private void validateDuplicatedUserNickname(String nickname) {
    userMapper.findUserByNickname(nickname).ifPresent(user -> {
      throw new DuplicatedValueException("중복된 닉네임이 존재합니다.");
    });
  }

  public LoginUserResonse loginUser(LoginUserRequest request) {
    User user = userMapper.findUserByUserId(request.getUserId()).orElseThrow(() -> {
      throw new NoSuchUserException("등록된 사용자가 아닙니다");
    });

    if (!isCheckedPassword(request.getPassword(), user.getPassword())) {
      throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
    }

    return LoginUserResonse.builder().userId(user.getUserId())
        .nickname(user.getNickname()).email(user.getEmail())
        .userPhoneNumber(user.getUserPhoneNumber()).createdAt(user.getCreatedAt())
        .createdBy(user.getCreatedBy()).updatedAt(user.getUpdatedAt())
        .updatedBy(user.getUpdatedBy()).idx(user.getIdx()).build();

  }

  private boolean isCheckedPassword(String password, String foundUserPassword) {
    return passwordEncoder.matches(password, foundUserPassword);
  }

}
