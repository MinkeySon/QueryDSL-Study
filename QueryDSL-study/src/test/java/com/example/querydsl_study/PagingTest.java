package com.example.querydsl_study;

import com.example.querydsl_study.data.entity.User;
import com.example.querydsl_study.data.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class PagingTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("페이징처리")
    void paging(){
        Pageable pageable = PageRequest.of(0,3);
        Page<User> userPage = userRepository.pageUsers(pageable);

        // 페이지 정보 출력
        System.out.println("총 페이지 수: " + userPage.getTotalPages());
        System.out.println("현재 페이지: " + userPage.getNumber());
        System.out.println("총 사용자 수: " + userPage.getTotalElements());

        // 사용자 목록 출력
        userPage.getContent().forEach(user -> {
            System.out.println("사용자 이름: " + user.getName() + ", 나이: " + user.getAge());
        });
    }
}
