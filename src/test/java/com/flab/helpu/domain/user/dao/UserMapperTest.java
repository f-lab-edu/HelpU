package com.flab.helpu.domain.user.dao;

import com.flab.helpu.domain.user.User;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Transactional // @Transactional은 클래스나 메서드에 붙여줄 경우, 해당 범위 내 메서드가 트랜잭션이 되도록 보장해준다.
@MybatisTest // mybatis unit 테스트를 위해 사용하는 어노테이션
class UserMapperTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  @DisplayName("User 객체 insert 쿼리 테스트")
  void insertUser() {
    User user = User.builder().userId("test1").password("qwer123456").nickname("테스트1")
        .userPhoneNumber("010-0000-0000").email("test1@test.com").createdBy("test1")
        .updatedBy("test1").build();

    userMapper.insertUser(user);

    User result = userMapper.findUserByUserId(user.getUserId())
        .orElseThrow(NullPointerException::new);

    Assertions.assertEquals(user.getUserId(), result.getUserId());

  }

  @Test
  @DisplayName("필수 정보를 넣지 않았을때 insert 실패")
  void failInsertUser() {
    //비밀번호 누락
    User user = User.builder().userId("test1").nickname("테스트1")
        .userPhoneNumber("010-0000-0000").email("test1@test.com").createdBy("test1")
        .updatedBy("test1").build();

//    아이디 누락
//    User user = User.builder().password("qwer123456").nickname("테스트1")
//        .userPhoneNumber("010-0000-0000").email("test1@test.com").createdBy("test1")
//        .updatedBy("test1").build();

    Assertions.assertThrows(DataIntegrityViolationException.class,
        () -> userMapper.insertUser(user));

  }

  @Test
  @DisplayName("잘못된 idx로 User 검색 시 검색 결과 없을때 Optional.empty 반환")
  void findNonExistentUserByIdx() {
    Long wrongIdx = -1L;

    Assertions.assertEquals(userMapper.findUserByIdx(wrongIdx), Optional.empty());

  }

  @Test
  @DisplayName("잘못된 userId로 User 검색 시 검색 결과 없을때 Optional.empty 반환")
  void findNonExistentUserByUserId() {
    String wrongUserId = "wrong";

    Assertions.assertEquals(userMapper.findUserByUserId(wrongUserId), Optional.empty());

  }

  @Test
  @DisplayName("잘못된 nickname으로 User 검색 시 검색 결과 없을때 Optional.empty 반환")
  void findNonExistentUserByNickname() {
    String wrongNickname = "wrong";

    Assertions.assertEquals(userMapper.findUserByNickname(wrongNickname), Optional.empty());

  }

}