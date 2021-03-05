package com.youngchayoungcha.tastynote.web;

import com.youngchayoungcha.tastynote.service.TokenService;
import com.youngchayoungcha.tastynote.web.dto.auth.AccessTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.RefreshTokenDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.SignInRequestDTO;
import com.youngchayoungcha.tastynote.web.dto.auth.TokenPairDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/signin")
    public ResponseEntity<TokenPairDTO> signIn(@RequestBody SignInRequestDTO requestDTO) {
        return ResponseEntity.ok(tokenService.signIn(requestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDTO> refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return ResponseEntity.ok(tokenService.refreshAccessToken(refreshTokenDTO));
    }

    //for jwt test. delete later.
    @GetMapping("/hello/hello")
    public ResponseEntity<String> hello(@RequestBody String str) {
        return ResponseEntity.ok("hello");
    }

    @DeleteMapping("/signout")
    public ResponseEntity signOut(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        tokenService.signOut(refreshTokenDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
