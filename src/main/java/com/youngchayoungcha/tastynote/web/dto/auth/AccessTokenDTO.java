package com.youngchayoungcha.tastynote.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AccessTokenDTO {
    private final String jwtAccessToken;

    @JsonCreator
    public AccessTokenDTO(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }
}
