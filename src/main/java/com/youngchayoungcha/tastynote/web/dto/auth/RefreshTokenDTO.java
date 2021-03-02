package com.youngchayoungcha.tastynote.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class RefreshTokenDTO {
    private final String refreshToken;

    @JsonCreator
    public RefreshTokenDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
