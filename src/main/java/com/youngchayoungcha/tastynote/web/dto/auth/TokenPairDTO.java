package com.youngchayoungcha.tastynote.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class TokenPairDTO {
    @NotEmpty(message = "클라이언트에게 토큰 발 시, JWT 접근 토큰은 반드시 명시되어야 합니다.")
    private final String jwtAccessToken;

    @NotEmpty(message = "클라이언트에게 토큰 발급 시, 리프레시 토큰은 반드시 명시되어야 합니다.")
    @JsonProperty("refresh_token")
    private final String refreshToken;

    @Builder
    public TokenPairDTO(String jwtAccessToken, String refreshToken) {
        this.jwtAccessToken = jwtAccessToken;
        this.refreshToken = refreshToken;
    }
}
