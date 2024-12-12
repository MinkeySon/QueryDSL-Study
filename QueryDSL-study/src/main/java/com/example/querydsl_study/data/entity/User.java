package com.example.querydsl_study.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id", "name", "age"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public User(String name, int age, Team team){
        this.name = name;
        this.age = age;
        this.team = team;
    }
}
