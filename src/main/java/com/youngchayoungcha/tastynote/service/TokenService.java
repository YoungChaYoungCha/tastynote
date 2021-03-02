package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.config.JwtAuthFilter;
import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.MemberRefreshToken;
//import com.youngchayoungcha.tastynote.domain.PasswordValidator;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import com.youngchayoungcha.tastynote.repository.MemberRefreshTokenRepository;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.util.JwtUtils;
import com.youngchayoungcha.tastynote.web.dto.auth.AccessTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.RefreshTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.SignInRequestDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.TokenPairDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtils jwtUtils;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final MemberRepository memberRepository;
//    private final PasswordValidator passwordValidator;

    @Transactional
    public TokenPairDTO signIn(SignInRequestDTO signInRequestDTO) {
        //user password validation 진행
        //만약에 맞다? 그러면 토큰 발급 후 return
        Member member = memberRepository.findByEmail(signInRequestDTO.getEmail()).orElseThrow(() ->
                new ElementNotFoundException(signInRequestDTO.getEmail(), ElementNotFoundException.ELEMENT_EMAIL));

//        this.passwordValidator.validate(
//                member,
//                signInRequestDTO.getPassword()
//        );

        String jwtAccessToken = jwtUtils.generateAccessToken(member.getEmail());
        String refreshToken = this.createRefreshToken(member);

        return TokenPairDTO.builder()
                            .jwtAccessToken(jwtAccessToken)
                            .refreshToken(refreshToken)
                            .build();
    }

    @Transactional
    public void signOut(RefreshTokenDTO refreshTokenDTO) {
        memberRefreshTokenRepository.findByToken(refreshTokenDTO.getRefreshToken())
                .ifPresent(memberRefreshTokenRepository::delete);
    }

    public AccessTokenDTO refreshAccessToken(RefreshTokenDTO refreshTokenDTO) {
        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByToken(refreshTokenDTO.getRefreshToken()).orElseThrow(() ->
                new ElementNotFoundException(refreshTokenDTO.getRefreshToken(), ElementNotFoundException.ELEMENT_TOKEN));

        return new AccessTokenDTO(jwtUtils.generateAccessToken(memberRefreshToken.getMember().getEmail()));
    }

    private String createRefreshToken(Member member) {
        String refreshToken = RandomStringUtils.randomAlphanumeric(128);
        memberRefreshTokenRepository.save(MemberRefreshToken.builder()
                                                            .member(member)
                                                            .token(refreshToken)
                                                            .build());
        return refreshToken;
    }
}
