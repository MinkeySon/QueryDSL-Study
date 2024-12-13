package com.example.querydsl_study.data.repository.support;

import com.example.querydsl_study.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    /**
     * name 이 같은지
     * @param name
     * @return User
     */
    User getUserByName(String name);

    /**
     * name 이랑 틀린 user 전부 조회
     * @param name
     * @return List
     */
    List<User> getUserNotEqualName(String name);

    /**
     * age 보다 적은 user 전부 조회
     * @param age
     * @return List
     */
    List<User> getUsersByLtAge(int age);
    /**
     * age 보다 적거나 같은 user 전부 조회
     * @param age
     * @return List
     */
    List<User> getUsersByLoeAge(int age);
    /**
     * age 많은 user 전부 조회
     * @param age
     * @return List
     */
    List<User> getUsersByGtAge(int age);
    /**
     * age 많거나 같은 user 전부 조회
     * @param age
     * @return List
     */
    List<User> getUsersByGoeAge(int age);
    /**
     * minAge 와 maxAge 사이에 있는 user 전부 조회
     * @param minAge, maxAge
     * @return List
     */
    List<User> getUsersByBetweenAge(int minAge, int maxAge);

    /**
     * age A or B
     * @param firstAge
     * @param secondAge
     * @return
     */
    List<User> getUsersByAorB(int firstAge, int secondAge);

    /**
     * age A nor B
     * @param firstAge
     * @param secondAge
     * @return
     */
    List<User> getUsersByANorB(int firstAge, int secondAge);

    /**
     * age sort asc
     * @param firstAge
     * @param secondAge
     * @return
     */
    List<User> sortUsersByAgeAsc(int firstAge, int secondAge);

    /**
     * age sort desc
     * @param firstAge
     * @param secondAge
     * @return
     */
    List<User> sortUsersByAgeDesc(int firstAge, int secondAge);

    /**
     * 페이징 처리
     * @return
     */
    Page<User> pageUsers(Pageable pageable);

    /**
     * 팀에 속한 유저들만 조회
     * @param firstAge
     * @param secondAge
     * @return
     */
    List<User> getUsersJoinTeam(int firstAge, int secondAge);
    /**
     * 팀에 속하지 않은 유저들도 조회
     * @param firstAge
     * @param secondAge
     * @return
     */
    List<User> getUsersLeftJoinTeam(int firstAge, int secondAge);
}
