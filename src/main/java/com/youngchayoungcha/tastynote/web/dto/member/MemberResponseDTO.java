package com.youngchayoungcha.tastynote.web.dto.member;

import com.youngchayoungcha.tastynote.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String email;
    private String name;
    private Boolean isCertified;

    public static MemberResponseDTO fromEntity(Member member) {
        MemberResponseDTO memberDTO = new MemberResponseDTO();
        memberDTO.id = member.getId();
        memberDTO.email = member.getEmail();
        memberDTO.name = member.getName();
        memberDTO.isCertified = member.getIsCertified();
        return memberDTO;
    }
}
