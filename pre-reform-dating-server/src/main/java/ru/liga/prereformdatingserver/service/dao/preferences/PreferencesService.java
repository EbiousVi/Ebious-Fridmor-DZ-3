package ru.liga.prereformdatingserver.service.dao.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profileDto.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.service.dao.repository.PreferencesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreferencesService {

    private final PreferencesRepository preferencesRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PreferencesService(PreferencesRepository preferencesRepository, JdbcTemplate jdbcTemplate) {
        this.preferencesRepository = preferencesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void findPreferencesByChatId(NewProfileDto newProfileDto) {
        List<Preferences> preferences = newProfileDto.getPreferences().stream()
                .map(pref -> new Preferences(null, newProfileDto.getChatId(), pref.name))
                .collect(Collectors.toList());
        try {
            preferencesRepository.saveAll(preferences);
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                throw new RuntimeException(e.getCause().getCause().getMessage());
            }
        }
    }

    public List<Preferences> findPreferencesByChatId(Long chatId) {
        return preferencesRepository.asdasd(chatId);
    }
/*    @Transactional
    public Preferences createPreferences(UserProfile userProfile) {
        String s = "insert into dating.preferences (chat_id, sex) values (?,?)";
        jdbcTemplate.update(s, userProfile.getChatId(), userProfile.getSex());
        return new Preferences(null, userProfile.getChatId(), userProfile.getSex());
    }*/
}

