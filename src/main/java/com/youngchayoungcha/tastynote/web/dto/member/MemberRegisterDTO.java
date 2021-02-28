package com.youngchayoungcha.tastynote.web.dto.member;

import com.youngchayoungcha.tastynote.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterDTO {
    private String email;
    private String password;
    private String name;

    @Builder
    public MemberRegisterDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static MemberRegisterDTO fromEntity(Member member) {
        MemberRegisterDTO memberDTO = new MemberRegisterDTO();
        memberDTO.email = member.getEmail();
        memberDTO.password = member.getPassword();
        memberDTO.name = member.getName();
        return memberDTO;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
