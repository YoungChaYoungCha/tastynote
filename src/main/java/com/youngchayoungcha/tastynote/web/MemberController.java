package com.youngchayoungcha.tastynote.web;

import com.youngchayoungcha.tastynote.service.MemberService;
import com.youngchayoungcha.tastynote.web.dto.member.MemberCertifyDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberRegisterDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberResponseDTO;
import com.youngchayoungcha.tastynote.web.dto.member.MemberUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> register(@RequestBody MemberRegisterDTO memberDTO) {
        MemberResponseDTO member = memberService.register(memberDTO);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> update(@PathVariable(value = "memberId") Long memberId,
                                            @RequestBody MemberUpdateDTO memberDTO) {
        MemberResponseDTO member = memberService.update(memberId, memberDTO);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PutMapping("/{memberId}/certify")
    public ResponseEntity<MemberResponseDTO> certify(@PathVariable(value = "memberId") Long memberId,
                                  @RequestBody MemberCertifyDTO memberDTO) {
        MemberResponseDTO member = memberService.certify(memberId, memberDTO);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    
    @DeleteMapping("/{memberId}")
    public ResponseEntity delete(@PathVariable(value = "memberId") Long memberId) {
        memberService.delete(memberId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
