package com.example.querydsl_study.data.repository;

import com.example.querydsl_study.data.entity.User;
import com.example.querydsl_study.data.repository.support.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findUserByName(String name);
    List<User> findUsersByNameIsNotLike(String name);
}
