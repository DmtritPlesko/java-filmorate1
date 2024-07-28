package ru.yandex.practicum.filmorate.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmRowMapper  {
public static Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = new Mpa(rs.getInt("mpa_id"));
        Film film = Film.builder()
                .id(rs.getLong("film_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .duration(rs.getLong("duration"))
                .releaseDate(rs.getDate("releaseDate").toLocalDate())
                .mpa(mpa)
                .genres(new LinkedHashSet<>())
                .likes(new LinkedHashSet<>())
                .build();

    System.out.println("qwpofoqpwfkopqwkfopqwpofkqwopfopqwkfpoq");
    System.out.println(mpa.getId()+ " - opqwfoqwkfoqwkQWMFOPQKFOPQWOPGKQWOPKGOPQWKGPOQWKGOPQWKGOPKQWOPGKQWOPKGPKQWG");
        return film;
    }
}
