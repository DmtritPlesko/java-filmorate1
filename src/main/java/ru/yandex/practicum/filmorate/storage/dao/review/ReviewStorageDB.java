package ru.yandex.practicum.filmorate.storage.dao.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.ReviewMapper;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReviewStorageDB implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;
    private final String save = "INSERT INTO feeds (user_id, entity_id, event_type, operation, time_stamp) " +
            "values (?, ?, ?, ?, ?)";

    @Override
    public Review create(Review review) {
        log.info("Добавление нового отзыва в бд");

        review.setUseful(0L);

        String sqlQuery = "INSERT INTO reviews (content, is_positive, user_id, film_id, useful) " +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement pr = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                pr.setString(1, review.getContent());
                pr.setBoolean(2, review.getIsPositive());
                pr.setLong(3, review.getUserId());
                pr.setLong(4, review.getFilmId());
                pr.setLong(5, review.getUseful());
                return pr;
            }, keyHolder);

            Number generatedKey = keyHolder.getKey();
            review.setReviewId(generatedKey.longValue());
            jdbcTemplate.update(save, review.getUserId(), review.getReviewId(), "REVIEW", "ADD",
                    LocalDateTime.now());
            return review;
        } catch (DataAccessException e) {
            log.error("Ошибка при добавлении отзыва: ", e);
            throw new NotFoundException("Неверные данные запроса");
        }
    }

    @Override
    public Review update(Review review) {
        log.info("Обновление отзыва с id: {}", review.getReviewId());
        getReviewById(review.getReviewId());

        final String sqlQuery = "UPDATE reviews SET content = ?, is_positive = ? WHERE review_id = ?";
        jdbcTemplate.update(sqlQuery,
                review.getContent(),
                review.getIsPositive(),
                review.getReviewId());
        jdbcTemplate.update(save, review.getUserId(), review.getReviewId(), "REVIEW", "UPDATE",
                LocalDateTime.now());
        return getReviewById(review.getReviewId());
    }

    @Override
    public void delete(Long reviewId) {
        Review review = getReviewById(reviewId);
        log.info("Удаление отзыва с id: {}", reviewId);
        getReviewById(reviewId);

        final String sqlQuery = "DELETE FROM reviews WHERE review_id = ?";
        jdbcTemplate.update(sqlQuery, reviewId);
        jdbcTemplate.update(save, review.getUserId(), review.getReviewId(), "REVIEW", "REMOVE",
                LocalDateTime.now());
    }

    @Override
    public List<Review> getAllReviewsByCount(Integer count) {
        log.info("Получение {} отзывов, отсортированных по рейтингу полезности", count);
        String sqlQuery = "SELECT * FROM reviews ORDER BY useful DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{count}, new ReviewMapper());
    }

    @Override
    public Review getReviewById(Long reviewId) {
        log.info("Поиск отзыва с id: {}", reviewId);
        String sqlQuery = "SELECT * FROM reviews WHERE review_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new Object[]{reviewId}, new ReviewMapper());
        } catch (DataAccessException e) {
            throw new NotFoundException("Отзыв с id: " + reviewId + " не найден" + e.getMessage());
        }
    }

    @Override
    public List<Review> getReviewsByFilmId(Long filmId, Integer count) {
        log.info("Получение {} отзывов, отсортированных по рейтингу полезности у фильма с id: {}", count, filmId);
        String sqlQuery = "SELECT * FROM reviews WHERE film_id = ? ORDER BY useful DESC LIMIT ?";
        try {
            return jdbcTemplate.query(sqlQuery, new Object[]{filmId, count}, new ReviewMapper());
        } catch (DataAccessException e) {
            throw new NotFoundException("Отзывы у фильма с id: " + filmId + " не найдены" + e.getMessage());
        }
    }

    @Override
    public void addReaction(Long reviewId, Long userId, Boolean isUseful) {
        log.info("Юзер с id: {} добавляет реакцию отзыву с id: {}", userId, reviewId);
        getReviewById(reviewId);

        String sqlQuery = "MERGE INTO review_likes (review_id, user_id, is_useful) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, reviewId, userId, isUseful);
        updateReviewUsefulRating(reviewId);
    }


    @Override
    public void deleteReaction(Long reviewId, Long userId) {
        log.info("Юзер с id: {} убирает реакцию отзыву с id: {}", userId, reviewId);
        getReviewById(reviewId);

        String sqlQuery = "DELETE FROM review_likes WHERE review_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, reviewId, userId);
        updateReviewUsefulRating(reviewId);
    }

    private long getReviewUseful(Long reviewId) {
        String sqlQuery = "SELECT SUM(" +
                "CASE WHEN is_useful = true THEN 1 ELSE -1 END) AS useful FROM review_likes " +
                "WHERE review_id = ?";
        Long result = jdbcTemplate.queryForObject(sqlQuery, new Object[]{reviewId}, Long.class);

        return result == null ? 0 : result;
    }

    private void updateReviewUsefulRating(Long reviewId) {
        long usefulRating = getReviewUseful(reviewId);

        String sqlQuery = "UPDATE reviews SET useful = ? WHERE review_id = ?";
        jdbcTemplate.update(sqlQuery, usefulRating, reviewId);
    }

}
