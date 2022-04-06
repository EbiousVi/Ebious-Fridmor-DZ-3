package ru.liga.prereformdatingserver.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.service.profile.UserProfileService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserProfileService userProfileService;

    @Override
    public UserDetails loadUserByUsername(String chatId) throws UsernameNotFoundException {
        return userProfileService.getUserProfileByChatId(Long.parseLong(chatId));
    }
}
