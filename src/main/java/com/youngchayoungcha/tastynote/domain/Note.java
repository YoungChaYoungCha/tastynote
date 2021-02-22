package com.youngchayoungcha.tastynote.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note {

    @Id
    @GeneratedValue
    @Column(name = "note_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    // 연관관계의 주체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // Lazy로 거는 것은 N+1 문제 때문
    // 해결하려면 JPQL Fetch join을 이용할 것.
    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public static Note createNote(String title, Member member){
        Note note = new Note();
        note.title = title;
        // 양방향 설정
        member.addNotes(note);
        note.member = member;
        return note;
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void modifyNote(String title) {
        this.title = title;
    }
}
