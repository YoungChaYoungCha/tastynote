package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.exception.CertifyNotCompleteException;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import com.youngchayoungcha.tastynote.exception.PasswordNotMatchedException;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.util.JwtUtils;
import com.youngchayoungcha.tastynote.web.dto.auth.AccessTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.RefreshTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.SignInRequestDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.TokenPairDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenPairDTO signIn(SignInRequestDTO signInRequestDTO) {
        Member member = memberRepository.findByEmail(signInRequestDTO.getEmail()).orElseThrow(() ->
                new ElementNotFoundException(signInRequestDTO.getEmail(), ElementNotFoundException.ELEMENT_EMAIL));

        if (member.getIsCertified()) {
            if (passwordEncoder.matches(signInRequestDTO.getPassword(), member.getPassword())) {
                String jwtAccessToken = jwtUtils.generateAccessToken(member.getId());
                member.generateRefreshToken();
                return TokenPairDTO.builder()
                        .jwtAccessToken(jwtAccessToken)
                        .refreshToken(member.getRefreshToken())
                        .build();
            } else throw new PasswordNotMatchedException();
        } else throw new CertifyNotCompleteException(member.getId());
    }

    @Transactional
    public void signOut(RefreshTokenDTO refreshTokenDTO) {
        memberRepository.findByRefreshToken(refreshTokenDTO.getRefreshToken())
                .ifPresent(member -> member.deleteRefreshToken());
    }

    public AccessTokenDTO refreshAccessToken(RefreshTokenDTO refreshTokenDTO) {
        Member member = memberRepository.findByRefreshToken(refreshTokenDTO.getRefreshToken()).orElseThrow(() ->
                new ElementNotFoundException(refreshTokenDTO.getRefreshToken(), ElementNotFoundException.ELEMENT_TOKEN));

        return new AccessTokenDTO(jwtUtils.generateAccessToken(member.getId()));
    }
}
