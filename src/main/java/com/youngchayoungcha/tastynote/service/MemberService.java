package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import com.youngchayoungcha.tastynote.exception.InvalidParameterException;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.web.dto.MailCertifiedDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberCertifyDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberRegisterDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberResponseDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDTO register(MemberRegisterDTO memberDTO) {
        Member member = Member.createMember(memberDTO.getEmail(),
                                            passwordEncoder.encode(memberDTO.getPassword()),
                                            memberDTO.getName());
        member.saveCertifiedKey();
        MemberResponseDTO responseDTO = MemberResponseDTO.fromEntity(memberRepository.save(member));

        Date now = new Date();
        MailCertifiedDTO mailCertifiedDTO = MailCertifiedDTO.builder()
                .address(member.getEmail())
                .title("test : " + now.toString())
                .certifiedKey((member.getCertifiedKey()))
                .build();

        emailService.sendEmail(mailCertifiedDTO);

        return responseDTO;
    }

    @Transactional
    public MemberResponseDTO update(Long memberId, MemberUpdateDTO memberDTO) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ElementNotFoundException(memberId));
        member.update(memberDTO.getPassword(), memberDTO.getName());
        return MemberResponseDTO.fromEntity(member);
    }

    @Transactional
    public MemberResponseDTO certify(Long memberId, MemberCertifyDTO memberDTO) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ElementNotFoundException(memberId));
        if (member.getCertifiedKey().equals(memberDTO.getKey())) {
            member.certify();
            return MemberResponseDTO.fromEntity(member);
        } else throw new InvalidParameterException("Certify key");
    }
    
    @Transactional
    public Long delete(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ElementNotFoundException(memberId));
        memberRepository.delete(member);
        return memberId;
    }
}
