package com.youngchayoungcha.tastynote.web.dto.member;

import com.youngchayoungcha.tastynote.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class MemberRegisterDTO {
    @NotEmpty(message = "멤버 이메일은 반드시 명시되어야 합니다.")
    private String email;
    @NotEmpty(message = "멤버 비밀번호는 반드시 명시되어야 합니다.")
    private String password;
    @NotEmpty(message = "멤버 이름은 반드시 명시되어야 합니다.")
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
