package com.youngchayoungcha.tastynote.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCertifyDTO {
    private String key;

    @Builder
    public MemberCertifyDTO(String key) {
        this.key = key;
    }
}
