package com.study.security.repository;

import com.study.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy 규칙 -> 이렇게 쓰면 select * from user where username= ? 이렇게 실행 됨
    public User findByUsername(String username);

}
