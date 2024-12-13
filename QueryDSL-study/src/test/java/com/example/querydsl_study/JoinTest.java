package com.example.querydsl_study;

import com.example.querydsl_study.data.entity.User;
import com.example.querydsl_study.data.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JoinTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("inner join 테스트")
    void innerJoinTest(){
        List<User> userList = userRepository.getUsersJoinTeam(20,29);
        System.out.println("result : " + userList.toString());
    }

    @Test
    @DisplayName("left join 테스트")
    void leftJoinTest(){
        List<User> userList = userRepository.getUsersLeftJoinTeam(20,29);
        System.out.println("result : " + userList.toString());
    }
}
