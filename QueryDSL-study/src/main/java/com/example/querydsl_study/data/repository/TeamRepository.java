package com.example.querydsl_study.data.repository;

import com.example.querydsl_study.data.entity.Team;
import com.example.querydsl_study.data.repository.support.TeamRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {
}
