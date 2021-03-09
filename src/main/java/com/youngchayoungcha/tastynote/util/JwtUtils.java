package com.youngchayoungcha.tastynote.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

@Component
@PropertySource("classpath:application-jwt.properties")
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.jwt.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.jwt.jwtExpirationMs}")
    private long jwtExpirationMs;

    public String generateAccessToken(Long userId) {
        Claims claims = Jwts.claims().setId(userId.toString());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, this.jwtSecret)
                .compact();
    }

    public Authentication getAuthentication(String jwtToken) {
        String userId = Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(jwtToken).getBody().getId();
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch(IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-ACCESS-TOKEN");
    }
}
