package com.youngchayoungcha.tastynote.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class AccessTokenDTO {
    @NotEmpty(message = "JWT 접근 토큰은 반드시 명시되어야 합니다.")
    private final String jwtAccessToken;

    @JsonCreator
    public AccessTokenDTO(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }
}
