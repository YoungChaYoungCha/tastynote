package com.youngchayoungcha.tastynote.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RefreshTokenDTO {
    @NotEmpty(message = "리프레시 토큰은 반드시 명시되어야 합니다.")
    private final String refreshToken;

    @JsonCreator
    public RefreshTokenDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
