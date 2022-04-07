package ru.liga.prereformdatingserver.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.security.domain.UserDetailsImpl;
import ru.liga.prereformdatingserver.repository.UserProfileRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String chatId) {
        UserProfile userProfile = userProfileRepository.findById(Long.parseLong(chatId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found by chatId = " + chatId));
        return new UserDetailsImpl(userProfile);
    }
}
