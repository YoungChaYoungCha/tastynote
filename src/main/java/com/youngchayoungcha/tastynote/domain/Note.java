package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
public class Note {

    @Id
    @GeneratedValue
    @Column(name = "note_id")
    private Long id;

    private String name;

    // 연관관계의 주체
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // Lazy로 거는 것은 N+1 문제 때문
    // 해결하려면 JPQL Fetch join을 이용할 것.
    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY)
    private Set<Post> posts = new LinkedHashSet<>();
}
