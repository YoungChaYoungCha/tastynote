package com.youngchayoungcha.tastynote.web.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateDTO {
    private String password;
    private String name;

    @Builder
    public MemberUpdateDTO(String password, String name) {
        this.password = password;
        this.name = name;
    }

}
