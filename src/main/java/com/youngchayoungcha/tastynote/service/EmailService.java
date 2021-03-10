package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.web.dto.MailCertifiedDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberCertifyDTO;

public interface EmailService {
    void sendEmail(MailCertifiedDTO mailCertifiedDTO);
}
