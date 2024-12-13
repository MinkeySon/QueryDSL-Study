package com.example.querydsl_study.data.repository.support;

import com.example.querydsl_study.data.entity.QTeam;
import com.example.querydsl_study.data.entity.QUser;
import com.example.querydsl_study.data.entity.User;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public User getUserByName(String name) {
        QUser quser = QUser.user;

        return jpaQueryFactory.selectFrom(quser)
                .where(quser.name.eq(name))
                .fetchOne();
    }

    @Override
    public List<User> getUserNotEqualName(String name) {
        QUser qUser = QUser.user;

        return jpaQueryFactory
                .select(qUser)
                .from(qUser)
                .where(qUser.name.eq(name).not())
                .fetch();
    }

    @Override
    public List<User> getUsersByLtAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.lt(age))
                .fetch();
    }

    @Override
    public List<User> getUsersByLoeAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.loe(age))
                .fetch();
    }

    @Override
    public List<User> getUsersByGtAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.gt(age))
                .fetch();
    }

    @Override
    public List<User> getUsersByGoeAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.goe(age))
                .fetch();
    }

    @Override
    public List<User> getUsersByBetweenAge(int minAge, int maxAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(minAge,maxAge))
                .fetch();
    }

    @Override
    public List<User> getUsersByAorB(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.in(firstAge,secondAge))
                .fetch();
    }

    @Override
    public List<User> getUsersByANorB(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.notIn(firstAge,secondAge))
                .fetch();
    }

    @Override
    public List<User> sortUsersByAgeAsc(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge,secondAge))
                .orderBy(qUser.age.asc())
                .fetch();
    }

    @Override
    public List<User> sortUsersByAgeDesc(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge,secondAge))
                .orderBy(qUser.age.desc())
                .fetch();
    }

    @Override
    public Page<User> pageUsers(Pageable pageable) {
        QUser qUser = QUser.user;

        List<User> userList = jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(20,29), qUser.team.name.eq("강한팀"))
                .orderBy(qUser.age.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPQLQuery<Long> count = jpaQueryFactory
                .select(qUser.count())
                .from(qUser)
                .where(qUser.age.between(20,29), qUser.team.name.eq("강한팀"));

        return PageableExecutionUtils.getPage(userList, pageable, count::fetchOne);
    }

    @Override
    public List<User> getUsersJoinTeam(int firstAge, int secondAge) {
        QUser qUser = QUser.user;
        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge, secondAge))
                .join(qUser.team)
                .fetch();
    }

    @Override
    public List<User> getUsersLeftJoinTeam(int firstAge, int secondAge) {
        QUser qUser = QUser.user;
        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge, secondAge))
                .leftJoin(qUser.team)
                .fetch();
    }
}
