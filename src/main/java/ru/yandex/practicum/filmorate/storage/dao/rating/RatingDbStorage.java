package ru.yandex.practicum.filmorate.storage.dao.rating;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.RatingMapper;
import ru.yandex.practicum.filmorate.model.Mpa;


import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class RatingDbStorage implements RatingDb {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getRatingById(Long id) {
        log.info("Пытаемся взять рейтинг с id = {}", id);
        final String sqlQuery = "select * from mpa where mpa_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, RatingMapper::mapRow);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new NotFoundException("Рейтин с id=" + id + " не найден");
        }
    }

    @Override
    public List<Mpa> getAllRating() {
        log.info("Берём все рейтинги которые есть в базе");
        final String sqlQuery = "select * from mpa";
        return jdbcTemplate.query(sqlQuery, RatingMapper::mapRow);
    }

}
