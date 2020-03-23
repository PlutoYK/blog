package com.ykcoding.dao;

import com.ykcoding.pojo.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userDao extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
