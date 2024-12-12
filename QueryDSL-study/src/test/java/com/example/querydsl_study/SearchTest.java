package com.example.querydsl_study;

import com.example.querydsl_study.data.entity.Team;
import com.example.querydsl_study.data.entity.User;
import com.example.querydsl_study.data.repository.TeamRepository;
import com.example.querydsl_study.data.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SearchTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    @DisplayName("유저 생성")
    void createUser(){
        List<Team> allTeam = teamRepository.getAllTeams();
        for(int i=1; i<=150; i++){
            int age = (int) (Math.random() * 50 + 10);
            int index = (int)(Math.random() * 3);
            User user = new User("test" + i, age, allTeam.get(index));
            userRepository.save(user);
        }
    }

    /**
     * 804ms
     */
    @Test
    @DisplayName("유저 이름으로 조회 JPA")
    void findUserByNameEqualJPA(){
        User user = userRepository.findUserByName("test79");

        System.out.println(user.toString());
    }

    /**
     * 1 sec 215ms
     */
    @Test
    @DisplayName("유저 이름으로 조회 QueryDSL")
    void findUserByNameEqualQueryDSL(){
        User user = userRepository.getUserByName("test79");

        System.out.println(user.toString());
    }

    /**
     * 758ms
     */
    @Test
    @DisplayName("유저 이름과 일치 하지 않은 유저들 조회 JPA")
    void findUsersByNameNotEqualJPA(){
        List<User> userList = userRepository.findUsersByNameIsNotLike("test79");
        System.out.println("test79 "+"이름이 아닌 유저 수 : "+userList.size());
    }

    /**
     * 1sec 116ms
     */
    @Test
    @DisplayName("유저 이름과 일치 하지 않은 유저들 조회 QueryDSL")
    void findUsersByNameNotEqualQueryDSL(){
        List<User> userList = userRepository.getUserNotEqualName("test79");
        System.out.println("test79 "+"이름이 아닌 유저 수 : "+userList.size());
    }

    /**
     * 1sec 126ms
     */
    @Test
    @DisplayName("25살보다 어린 유저들 조회")
    void findUsersLessThan25(){
        List<User> userList = userRepository.getUsersByLtAge(25);
        System.out.println("유저 수 : "+userList.size());
    }

    /**
     * 1sec 143ms
     */
    @Test
    @DisplayName("25살보다 어리거나 같은 유저들 조회")
    void findUsersLessOrEqual25(){
        List<User> userList = userRepository.getUsersByLoeAge(25);
        System.out.println("유저 수 : "+userList.size());
    }

    /**
     * 1sec 176ms
     */
    @Test
    @DisplayName("25살보다 많은 유저들 조회")
    void findUsersMoreThan25(){
        List<User> userList = userRepository.getUsersByGtAge(25);
        System.out.println("유저 수 : "+userList.size());
    }

    /**
     * 1sec 202ms
     */
    @Test
    @DisplayName("25살보다 많거나 같은 유저들 조회")
    void findUsersMoreOrEqual25(){
        List<User> userList = userRepository.getUsersByGoeAge(25);
        System.out.println("유저 수 : "+userList.size());
    }
    @Test
    @DisplayName("20대 유저들 조회")
    void findUsersTwenty(){
        List<User> userList = userRepository.getUsersByBetweenAge(20,29);
        System.out.println(userList.toString());
    }
    @Test
    @DisplayName("유저들 조회 A or B")
    void findUsersAorB(){
        List<User> userList = userRepository.getUsersByAorB(20,29);
        System.out.println(userList.toString());
    }
    @Test
    @DisplayName("유저들 조회 A nor B")
    void findUsersANorB(){
        List<User> userList = userRepository.getUsersByANorB(20,29);
        System.out.println(userList.toString());
    }
}
