package com.youngchayoungcha.tastynote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String certifiedKey;

    private Boolean isCertified;

    private String refreshToken;

    // 연관관계의 종
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Set<Note> notes = new LinkedHashSet<>();

    @PrePersist
    public void prePersist() {
        this.isCertified = this.isCertified == null ? false : this.isCertified;
    }

    @Builder
    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

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

    public Member update(String password, String name) {
        this.password = password;
        this.name = name;
        return this;
    }

    public void certify() {
        this.isCertified = true;
    }

    public void saveCertifiedKey() {
        this.certifiedKey = generateCertifiedKey();
    }

    //random 10자리 문자열 생성
    private String generateCertifiedKey() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;
        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 10);
        return sb.toString();
    }

    public void generateRefreshToken() {
        String refreshToken = RandomStringUtils.randomAlphanumeric(128);
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

}
