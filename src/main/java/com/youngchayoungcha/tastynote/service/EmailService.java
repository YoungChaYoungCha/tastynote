package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.web.dto.MailCertifiedDTO;

public interface EmailService {
    void sendEmail(MailCertifiedDTO mailCertifiedDTO);
}
