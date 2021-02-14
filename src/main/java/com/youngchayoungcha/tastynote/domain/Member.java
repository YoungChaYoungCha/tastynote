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

    protected Member(){

    }

    // 연관관계의 종
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Set<Note> notes = new LinkedHashSet<>();

    public void addNotes(Note note){
        notes.add(note);
        note.setMember(this);
    }

    public static Member createMember(String email, String password, String name){
        Member member = new Member();
        member.email= email;
        member.password = password;
        member.name = name;
        return member;
    }
}
