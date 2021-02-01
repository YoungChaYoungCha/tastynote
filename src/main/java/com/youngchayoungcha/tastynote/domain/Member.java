package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String name;

    // 연관관계의 종
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Set<Note> notes = new LinkedHashSet<>();
}
