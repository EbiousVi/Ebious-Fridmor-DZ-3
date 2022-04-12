package ru.liga.prereformdatingserver.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformdatingserver.domain.dto.auth.LoginDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.mapper.UserProfileDtoMapper;
import ru.liga.prereformdatingserver.service.profile.UserProfileServiceImpl;

import javax.security.auth.message.AuthException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserProfileServiceImpl userProfileServiceImpl;
    private final UserProfileDtoMapper userProfileDtoMapper;

    @PostMapping("/dating-server/auth/login")
    public UserProfileDto login(@RequestBody LoginDto loginDto) throws AuthException {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getChatId(), loginDto.getPassword()));
            if (authenticate == null) throw new AuthException("Auth failed!");
            long id = Long.parseLong(authenticate.getName());
            UserProfile authUserProfile = userProfileServiceImpl.getUserProfileById(id);
            return userProfileDtoMapper.map(authUserProfile);
        } catch (BadCredentialsException e) {
            log.info("Login attempt with invalid credentials {}", loginDto, e);
            throw new AuthException("Invalid credentials");
        }
    }
}
