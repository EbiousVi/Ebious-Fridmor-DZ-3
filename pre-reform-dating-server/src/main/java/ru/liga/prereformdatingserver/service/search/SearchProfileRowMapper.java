package ru.liga.prereformdatingserver.service.search;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SearchProfileRowMapper implements RowMapper<SearchProfileProjection> {

    private static final String CHAT_ID = "chat_id";
    private static final String NAME = "name";
    private static final String SEX = "sex";
    private static final String DESCRIPTION = "description";
    private static final String POTENTIAL_MATCH = "potential_match";

    public SearchProfileProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SearchProfileProjection(
                rs.getLong(CHAT_ID),
                rs.getString(NAME),
                rs.getString(SEX),
                rs.getString(DESCRIPTION),
                rs.getBoolean(POTENTIAL_MATCH));
    }
}
