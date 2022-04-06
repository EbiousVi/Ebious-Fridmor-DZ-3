package ru.liga.prereformdatingserver.service.search;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SearchProfileRowMapper implements RowMapper<SearchProfileProjection> {

    private static final int CHAT_ID = 1;
    private static final int NAME = 2;
    private static final int SEX = 3;
    private static final int AVATAR = 4;
    private static final int POTENTIAL_MATCH = 5;

    public SearchProfileProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SearchProfileProjection(
                rs.getLong(CHAT_ID),
                rs.getString(NAME),
                rs.getString(SEX),
                rs.getString(AVATAR),
                rs.getBoolean(POTENTIAL_MATCH));
    }
}
