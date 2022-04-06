package ru.liga.prereformdatingserver.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.security.service.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_LENGTH = 7;
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final long ACCESS_TOKEN_EXPIRATION = 86_400_000;
    private static final long REFRESH_TOKEN_EXPIRATION = 86_400_000;
    private final UserDetailsServiceImpl userDetailsService;
    //@Value("${signature.secret}")
    private String signature = "SECRET";

    public String generateAccessToken(String chatId) {
        return generateToken(chatId, ACCESS_TOKEN_EXPIRATION, false);
    }

    public String generateRefreshToken(String chatId) {
        return generateToken(chatId, REFRESH_TOKEN_EXPIRATION, true);
    }

    private String generateToken(String chatId, long validityInMilliseconds, boolean isRefresh) {
        Claims claims = Jwts.claims().setSubject(chatId);
        claims.put(REFRESH_TOKEN, isRefresh);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.info("Access token is expired", e);
            throw new JwtAuthException("Access token is expired");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.warn("Invalid token = {}", token, e);
            throw new JwtAuthException("Invalid token, Authentication failed! Try again!");
        } catch (SignatureException e) {
            log.error("Invalid signature !!!", e);
            throw new JwtAuthException("Authentication failed! Try again!");
        }
    }

    public String getChatIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signature)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getChatIdFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_LENGTH);
        } else {
            return null;
        }
    }

    public Map<String, String> getTokens(UserProfile userProfile) {
        String accessToken = this.generateAccessToken(String.valueOf(userProfile.getChatId()));
        String refreshToken = this.generateRefreshToken(String.valueOf(userProfile.getChatId()));
        Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN, accessToken);
        tokens.put(REFRESH_TOKEN, refreshToken);
        return tokens;
    }
}
