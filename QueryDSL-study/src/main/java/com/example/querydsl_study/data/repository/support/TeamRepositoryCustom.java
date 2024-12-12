package com.example.querydsl_study.data.repository.support;

import com.example.querydsl_study.data.entity.Team;

import java.util.List;

public interface TeamRepositoryCustom {
    List<Team> getAllTeams();
}
