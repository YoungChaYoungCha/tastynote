package com.youngchayoungcha.tastynote.web.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestDTO {
    private String email;
    private String password;

    @Builder
    public SignInRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
