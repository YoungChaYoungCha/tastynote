package com.youngchayoungcha.tastynote.controller;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.util.JwtUtils;
import com.youngchayoungcha.tastynote.web.dto.auth.AccessTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.RefreshTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.SignInRequestDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.TokenPairDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberCertifyDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberRegisterDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberResponseDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

//    @Autowired
//    private MemberRefreshTokenRepository memberRefreshTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    String sharedUrl;
    String cheonEmail = "hhj2134@naver.com";
    String cheonPassword = "1234";

    @Before
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        sharedUrl = "http://localhost:" + port;
    }

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void signInAndCertify() {
        MemberRegisterDTO memberDTO = MemberRegisterDTO.builder()
                .email(cheonEmail)
                .password(cheonPassword)
                .name("jhcheon")
                .build();

        String url = sharedUrl + "/member/signup";

        ResponseEntity<MemberResponseDTO> responseEntity = restTemplate.postForEntity(url, memberDTO, MemberResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Member> memberList = memberRepository.findAll();
        String key = memberList.get(0).getCertifiedKey();
        Long memberId = memberList.get(0).getId();
        assertThat(memberList.get(0).getIsCertified()).isEqualTo(false);

        MemberCertifyDTO memberCertifyDTO = new MemberCertifyDTO(key);

        String url2 = sharedUrl + "/member/" + memberId +"/certify";
        HttpEntity<MemberCertifyDTO> requestEntity = new HttpEntity<>(memberCertifyDTO);
        ResponseEntity<MemberResponseDTO> responseEntity2 = restTemplate.exchange(url2, HttpMethod.PUT, requestEntity, MemberResponseDTO.class);

        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Member> memberList2 = memberRepository.findAll();
        assertThat(memberList2.get(0).getIsCertified()).isEqualTo(true);
    }

    public void signInAndCertifyAndLogin() {

    }

    @Test
    public void Token_로그인및리프레시통한재발급및로그아웃() throws Exception {
        //1. 로그인
        signInAndCertify();
        String url = sharedUrl + "/token/signin";

        SignInRequestDTO signInRequestDTO = SignInRequestDTO.builder()
                                                            .email(cheonEmail)
                                                            .password(cheonPassword)
                                                            .build();

        ResponseEntity<TokenPairDTO> responseEntity = restTemplate.postForEntity(url, signInRequestDTO, TokenPairDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        String jwtToken = responseEntity.getBody().getJwtAccessToken();
        String refreshToken = responseEntity.getBody().getRefreshToken();
        assertThat(jwtUtils.validateToken(jwtToken)).isEqualTo(true);
        assertThat(memberRepository.findAll().get(0).getRefreshToken()).isEqualTo(refreshToken);

        //2. 리프레시 토큰을 통한 jwt 액세스 토큰 재발급
        RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO(refreshToken);
        String url2 = sharedUrl + "/token/refresh";

        ResponseEntity<AccessTokenDTO> responseEntity2 = restTemplate.postForEntity(url2, refreshTokenDTO, AccessTokenDTO.class);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);

        String newJwtToken = responseEntity2.getBody().getJwtAccessToken();
        assertThat(jwtUtils.validateToken(newJwtToken)).isEqualTo(true);

        //3. 로그아웃
        String url3 = sharedUrl + "/token/signout";
        HttpEntity<RefreshTokenDTO> requestEntity = new HttpEntity<>(refreshTokenDTO);
        ResponseEntity<Boolean> responseEntity3 = restTemplate.exchange(url3, HttpMethod.DELETE, requestEntity, Boolean.class);

        assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity3.getBody().booleanValue()).isEqualTo(true);
        assertThat(memberRepository.findAll().get(0).getRefreshToken()).isEqualTo(null);
    }
}
