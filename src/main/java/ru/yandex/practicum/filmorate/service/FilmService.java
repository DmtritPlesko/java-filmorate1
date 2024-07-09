package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.storage.filmstorage.FilmStorageInterface;
import ru.yandex.practicum.filmorate.storage.filmstorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.userstorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.userstorage.UserStorageInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private FilmStorageInterface filmStorage;
    private UserStorageInterface userStorage;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    public FilmService(FilmStorageInterface filmStorage, UserStorageInterface userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addNewFilm(Film film) {
        checkValidation(film);
        filmStorage.addNewFilm(film);
    }

    public void update(Film film) {
        if (filmStorage.getFilmByID(film.getId()) == null) {
            log.error("Фильм с идентефикатором : {}", film.getId() + " не найден");
            return;
        }
        checkValidation(film);

        filmStorage.update(film);
    }

    public Collection<Film> allFilm() {
        return filmStorage.allFilms();
    }

    public Film getFilmByID(Long filmId) {
        checkContainsById(filmId);

        return filmStorage.getFilmByID(filmId);
    }

    public void takeLike(Long id, Long userId) {
        if (userStorage.getUserById(id) == null) {
            throw new NotFoundException("Пользователь с id = " + id + " не существует");
        }

        checkContainsById(id);

        filmStorage.takeLike(id, userId);
    }

    public void deleteLike(Long id, Long userId) {

        if (userStorage.getUserById(id) == null) {
            throw new NotFoundException("Пользователь с id = " + id + " не существует");
        }
        checkContainsById(id);

        filmStorage.deleteLike(id, userId);
    }

    public List<Film> getPopularFilm(int count) {
        return filmStorage.allFilms().stream()
                .filter(n -> n.getLikes() != null)
                .sorted((n, f) -> f.getLikes().size() - n.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkContainsById(Long filmId) {

        if (filmStorage.getFilmByID(filmId) == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
    }

    private void checkValidation(Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            log.error("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } else if ((film.getReleaseDate().isBefore(LocalDate.parse("28.12.1895", formatter)))) {
            log.error("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration().getSeconds() < 0) {
            log.error("Продолжительность не может быть отрицательной");
            throw new ValidationException("Продолжительность не может быть отрицательной");
        }
    }
}

