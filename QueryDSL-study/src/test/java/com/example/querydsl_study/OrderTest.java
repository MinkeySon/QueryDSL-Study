package com.example.querydsl_study;

import com.example.querydsl_study.data.entity.User;
import com.example.querydsl_study.data.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderTest {
    @Autowired
    UserRepository userRepository;
    @Test
    @DisplayName("20대 중 나이순으로 오름차순 정렬")
    void orderUsersByAgeAsc(){
        List<User> userList = userRepository.sortUsersByAgeAsc(20,29);
        System.out.println("결과 : " + userList.toString());
    }
    @Test
    @DisplayName("20대 중 나이순으로 내림차순 정렬")
    void orderUsersByAgeDesc(){
        List<User> userList = userRepository.sortUsersByAgeDesc(20,29);
        System.out.println("결과 : " + userList.toString());
    }
}
