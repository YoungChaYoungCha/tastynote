package com.youngchayoungcha.tastynote.controller;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.web.dto.member.MemberCertifyDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberRegisterDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberResponseDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberUpdateDTO;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void Member_간단회원가입() throws Exception {
        MemberRegisterDTO memberDTO = MemberRegisterDTO.builder()
                .email("hhj2134@naver.com")
                .password("1234")
                .name("jhcheon")
                .build();

        String url = "http://localhost:" + port + "/member/signup";

        ResponseEntity<MemberResponseDTO> responseEntity = restTemplate.postForEntity(url, memberDTO, MemberResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(0).getEmail()).isEqualTo("hhj2134@naver.com");
        assertThat(memberList.get(0).getPassword()).isEqualTo("1234");
        assertThat(memberList.get(0).getName()).isEqualTo("jhcheon");
        assertThat(memberList.get(0).getIsCertified()).isEqualTo(false);
    }

    @Test
    public void Member_인증키확인() throws Exception {
        MemberRegisterDTO memberDTO = MemberRegisterDTO.builder()
                .email("hhj2134@naver.com")
                .password("1234")
                .name("jhcheon")
                .build();

        String url = "http://localhost:" + port + "/member/signup";

        ResponseEntity<MemberResponseDTO> responseEntity = restTemplate.postForEntity(url, memberDTO, MemberResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Member> memberList = memberRepository.findAll();
        String key = memberList.get(0).getCertifiedKey();
        Long memberId = memberList.get(0).getId();
        assertThat(memberList.get(0).getIsCertified()).isEqualTo(false);

        MemberCertifyDTO memberCertifyDTO = new MemberCertifyDTO(key);

        String url2 = "http://localhost:" + port + "/member/" + memberId +"/certify";
        HttpEntity<MemberCertifyDTO> requestEntity = new HttpEntity<>(memberCertifyDTO);
        ResponseEntity<MemberResponseDTO> responseEntity2 = restTemplate.exchange(url2, HttpMethod.PUT, requestEntity, MemberResponseDTO.class);

        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Member> memberList2 = memberRepository.findAll();
        assertThat(memberList2.get(0).getIsCertified()).isEqualTo(true);
    }

    @Test
    public void Member_이메일중복확인() throws Exception {
        MemberRegisterDTO memberDTO = MemberRegisterDTO.builder()
                .email("hhj2134@naver.com")
                .password("1234")
                .name("jhcheon")
                .build();

        String url = "http://localhost:" + port + "/member/signup";

        ResponseEntity<MemberResponseDTO> responseEntity = restTemplate.postForEntity(url, memberDTO, MemberResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberRegisterDTO memberDTO2 = MemberRegisterDTO.builder()
                .email("hhj2134@naver.com")
                .password("1111")
                .name("hoonki")
                .build();

        ResponseEntity<MemberResponseDTO> responseEntity2 = restTemplate.postForEntity(url, memberDTO2, MemberResponseDTO.class);

        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void Member_회원정보수정() throws Exception {
        Member savedMember = memberRepository.save(Member.builder()
                                                        .email("hhj2134@naver.com")
                                                        .password("1234")
                                                        .name("jhcheon")
                                                        .build());

        Long savedId = savedMember.getId();
        String expectedPwd = "4321";
        String expectedName = "JunHyeon Cheon";

        MemberUpdateDTO memberDTO = MemberUpdateDTO.builder()
                                        .password(expectedPwd)
                                        .name(expectedName)
                                        .build();

        String url = "http://localhost:" + port + "/member/" + savedId;

        HttpEntity<MemberUpdateDTO> requestEntity = new HttpEntity<>(memberDTO);
        ResponseEntity<MemberResponseDTO> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, MemberResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(0).getName()).isEqualTo(expectedName);
        assertThat(memberList.get(0).getPassword()).isEqualTo(expectedPwd);
    }

    @Test
    public void Member_회원삭제() throws Exception {
        Member savedMember = memberRepository.save(Member.builder()
                .email("hhj2134@naver.com")
                .password("1234")
                .name("jhcheon")
                .build());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.size()).isEqualTo(1);

        memberRepository.delete(savedMember);

        List<Member> memberList2 = memberRepository.findAll();
        assertThat(memberList2.size()).isEqualTo(0);
    }

}
