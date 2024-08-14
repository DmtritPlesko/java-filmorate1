package ru.yandex.practicum.filmorate.storage.dao.genres;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.GenresMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmGenresDbStorage implements FilmGenres {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre getGenresById(Long id) {
        log.info("Пытаемся взять жанр с id = {}", id);
        final String sqlQuery = "select * from genres where genre_id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, GenresMapper::mapRow, id);

    }

    @Override
    public List<Genre> getAllGenres() {
        log.info("Береём все жанры");
        final String sqlQuery = "select * from genres;";
        return jdbcTemplate.query(sqlQuery, GenresMapper::mapRow);
    }

}
