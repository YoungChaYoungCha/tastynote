package com.youngchayoungcha.tastynote.web.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MailCertifiedDTO {
    @NotEmpty(message = "수신 메일 주소는 반드시 명시되어야 합니다.")
    private String address;
    private String title;
    @NotEmpty(message = "이메일 인증 키는 반드시 명시되어야 합니다.")
    private String certifiedKey;

    @Builder
    public MailCertifiedDTO(String address, String title, String certifiedKey) {
        this.address = address;
        this.title = title;
        this.certifiedKey = certifiedKey;
    }
}
