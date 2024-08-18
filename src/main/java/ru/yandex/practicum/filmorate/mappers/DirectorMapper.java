package ru.yandex.practicum.filmorate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectorMapper implements RowMapper<Director> {
    public  Director mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Director(rs.getLong("director_id"),
                rs.getString("director_name"));
    }
}
