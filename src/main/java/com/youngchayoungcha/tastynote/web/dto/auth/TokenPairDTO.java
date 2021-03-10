package com.youngchayoungcha.tastynote.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenPairDTO {
    private final String jwtAccessToken;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    @Builder
    public TokenPairDTO(String jwtAccessToken, String refreshToken) {
        this.jwtAccessToken = jwtAccessToken;
        this.refreshToken = refreshToken;
    }
}
