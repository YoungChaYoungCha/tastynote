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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final EmailServiceImpl emailServiceImpl;

    @Transactional
    public MemberResponseDTO register(MemberRegisterDTO memberDTO) {
        Member member = Member.createMember(memberDTO.getEmail(), memberDTO.getPassword(),memberDTO.getName());
        member.saveCertifiedKey();
        MemberResponseDTO responseDTO = MemberResponseDTO.fromEntity(memberRepository.save(member));

        MailCertifiedDTO mailCertifiedDTO = MailCertifiedDTO.builder()
                .address(member.getEmail())
                .title("test")
                .certifiedKey((member.getCertifiedKey()))
                .build();

        emailServiceImpl.sendEmail(mailCertifiedDTO);

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
        } else throw new InvalidParameterException("Certify Key");
    }
    
    @Transactional
    public Long delete(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ElementNotFoundException(memberId));
        memberRepository.delete(member);
        return memberId;
    }

//    public void sendEmail(MailCertifiedDTO mailDTO) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(mailDTO.getAddress());
//        message.setFrom(MemberService.FROM_ADDRESS);
//        message.setSubject(mailDTO.getTitle());
//        message.setText(mailDTO.getCertifiedKey());
//
//        javaMailSender.send(message);
//    }
//    @org.springframework.transaction.annotation.Transactional(readOnly = true)
//    public MemberDTO findMemberByEmail(String email) {
//        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new DuplicateEmailException(email));
//        return MemberDTO.fromEntity(member);
//    }


}
