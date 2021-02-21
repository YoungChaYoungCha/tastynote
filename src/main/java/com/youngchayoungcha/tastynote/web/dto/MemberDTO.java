package com.youngchayoungcha.tastynote.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO {

    private Long id;

    private String email;
    private String password;
    private String name;
}
