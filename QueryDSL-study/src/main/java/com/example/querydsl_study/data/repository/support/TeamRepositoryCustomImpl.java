package com.example.querydsl_study.data.repository.support;

import com.example.querydsl_study.data.entity.QTeam;
import com.example.querydsl_study.data.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public TeamRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Team> getAllTeams() {
        QTeam qTeam = QTeam.team;

        return jpaQueryFactory.select(qTeam)
                .from(qTeam)
                .fetch();
    }
}
