package com.flab.helpu.domain.user.dao;

import com.flab.helpu.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

/*
 * MyBatis는 최소 하나의 SqlSessionFactory와 Mapper Interface가 필요하며
 * Mapper Interface 를 만들어주는 애노테이션입니다.
 */
@Mapper
public interface UserMapper {

  void insertUser(User user);

  Optional<User> findUserByIdx(Long idx);

  Optional<User> findUserByUserId(String userId);

  Optional<User> findUserByNickname(String nickname);

}
