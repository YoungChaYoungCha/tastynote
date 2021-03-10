package com.youngchayoungcha.tastynote.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MailCertifiedDTO {
    private String address;
    private String title;
    private String certifiedKey;

    @Builder
    public MailCertifiedDTO(String address, String title, String certifiedKey) {
        this.address = address;
        this.title = title;
        this.certifiedKey = certifiedKey;
    }
}
