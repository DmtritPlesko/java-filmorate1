package ru.yandex.practicum.filmorate.storage.dao.genres;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.GenresMapper;
import ru.yandex.practicum.filmorate.mappers.RatingMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmGenresDbStorage implements FilmGenres {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre getGenresById(Long id) {
        log.info("Пытаемся взять жанр с id = {}",id);
        final String sqlQuery = "select * from genres where genre_id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery,GenresMapper::mapRow,id);

    }

    @Override
    public List<Genre> getAllGenres() {
        log.info("Береём все жанры");
        final String sqlQuery = "select * from genres;";
        List<Genre> genres = jdbcTemplate.query(sqlQuery,GenresMapper::mapRow);
        return  genres;
    }
}
