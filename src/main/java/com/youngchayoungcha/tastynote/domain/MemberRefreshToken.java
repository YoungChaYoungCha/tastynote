package com.youngchayoungcha.tastynote.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Member member;

    @Builder
    public MemberRefreshToken(String token, Member member) {
        this.token = token;
        this.member = member;
    }
}
