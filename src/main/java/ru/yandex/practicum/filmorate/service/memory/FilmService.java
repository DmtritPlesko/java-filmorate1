package ru.yandex.practicum.filmorate.service.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorageInterface;
import ru.yandex.practicum.filmorate.storage.UserStorageInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Slf4j
//@Service
//public class FilmService {
//    private FilmStorageInterface filmStorage;
//    private UserStorageInterface userStorage;
//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//
//    @Autowired
//    public FilmService(FilmStorageInterface filmStorage, UserStorageInterface userStorage) {
//        this.filmStorage = filmStorage;
//        this.userStorage = userStorage;
//    }
//
//    public Film addNewFilm(Film film) {
//        checkValidation(film);
//        return filmStorage.addNewFilm(film);
//    }
//
//    public Film update(Film newFilm) {
//        Film film = filmStorage.getFilmByID(newFilm.getId());
//
//        if (film == null) {
//            log.error("Фильм с id = " + film.getId() + " не найден");
//            throw new NotFoundException("Фильм с id = " + film.getId() + " не найден");
//        }
//
//        checkValidation(film);
//
//        return filmStorage.update(film);
//    }
//
//    public Collection<Film> allFilm() {
//        return filmStorage.allFilms();
//    }
//
//    public Film getFilmByID(Long filmId) {
//
//        return filmStorage.getFilmByID(filmId);
//    }
//
//    public void takeLike(Long id, Long userId) {
//
//        User user = userStorage.getUserById(userId);
//
//        Set<Long> takeUserLike = filmStorage.getFilmByID(id).getLikes();
//
//        if(takeUserLike.contains(userId)) {
//            log.error("Пользователь с id = {} уже поставил лайк",userId);
//            throw new IllegalArgumentException("Пользователь с id = " + userId + " уже поставил лайк");
//        }
//
//        log.info("Пользователь с id = {} лайкнул фильм с id = {}",userId,id);
//        filmStorage.takeLike(id, userId);
//    }
//
//    public void deleteLike(Long id, Long userId) {
//        Set<Long> userLikes = filmStorage.getFilmByID(id).getLikes();
//
//        if(!userLikes.contains(userId)) {
//            log.error("ОЙ! пользотватель с id = {} не лайкал фильм с id = {}",userId,id);
//            throw new NotFoundException("ОЙ! пользотватель с id = " + userId + " не лайкал фильм с id = "+ id );
//        }
//
//        log.info("Пользовтаель с id = {} удалил свой лайк с фильма под id = {} ",userId,id);
//        filmStorage.deleteLike(id, userId);
//    }
//
//    public List<Film> getPopularFilm(int count) {
//        return filmStorage.allFilms().stream()
//                .filter(n -> n.getLikes() != null)
//                .sorted((n, f) -> f.getLikes().size() - n.getLikes().size())
//                .limit(count)
//                .collect(Collectors.toList());
//    }
//
//    private void checkValidation(Film film){
//        if (film.getName().isBlank()) {
//            log.error("Название не может быть пустым");
//            throw new ValidationException("Название не может быть пустым");
//        } else if (film.getDescription().length() > 200) {
//            log.error("Максимальная длина описания — 200 символов");
//            throw new ValidationException("Максимальная длина описания — 200 символов");
//        } else if ((film.getReleaseDate().isBefore(LocalDate.parse("28.12.1895", formatter)))) {
//            log.error("Дата релиза — не раньше 28 декабря 1895 года");
//            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
//        } else if (film.getDuration()< 0) {
//            log.error("Продолжительность не может быть отрицательной");
//            throw new ValidationException("Продолжительность не может быть отрицательной");
//        }
//    }
//}
//
