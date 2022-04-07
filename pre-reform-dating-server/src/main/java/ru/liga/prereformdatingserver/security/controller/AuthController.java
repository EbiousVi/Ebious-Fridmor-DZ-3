package ru.liga.prereformdatingserver.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformdatingserver.domain.dto.auth.LoginDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.security.jwt.JwtTokenProvider;
import ru.liga.prereformdatingserver.service.profile.UserProfileService;

import javax.security.auth.message.AuthException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserProfileService userProfileService;

    @PostMapping("/dating-server/auth/login")
    public Map<String, String> login(@RequestBody LoginDto loginDto) throws AuthException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getChatId(), loginDto.getPassword()));
            UserProfile authUserProfile = userProfileService.getUserProfileById(Long.valueOf(loginDto.getChatId()));
            return jwtTokenProvider.getTokens(authUserProfile);
        } catch (BadCredentialsException e) {
            log.info("Login attempt with invalid credentials {}", loginDto, e);
            throw new AuthException("Invalid credentials");
        }
    }
}
