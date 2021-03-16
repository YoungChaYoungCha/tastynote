package com.youngchayoungcha.tastynote.web.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SignInRequestDTO {
    @NotEmpty(message = "로그인 시, 멤버 이메일은 반드시 명시되어야 합니다.")
    private String email;
    @NotEmpty(message = "로그인 시, 멤버 비밀번호는 반드시 명시되어야 합니다.")
    private String password;

    @Builder
    public SignInRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
