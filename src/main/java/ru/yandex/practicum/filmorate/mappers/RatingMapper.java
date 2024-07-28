package ru.yandex.practicum.filmorate.mappers;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper  {
    public static Mpa mapRow(ResultSet rs, int mapRow) throws SQLException {
        return new Mpa(rs.getInt("mpa_id"),rs.getString("mpa_name"));
    }
}
