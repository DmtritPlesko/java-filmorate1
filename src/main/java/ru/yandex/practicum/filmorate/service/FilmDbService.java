package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorageInterface;
import ru.yandex.practicum.filmorate.storage.dao.filmDb.FilmDbStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class FilmDbService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final FilmStorageInterface filmStorage;
    private final UserDbService userDbService;

    @Autowired
    public FilmDbService(FilmDbStorage filmDbStorage, UserDbService userDbService) {
        this.filmStorage = filmDbStorage;
        this.userDbService = userDbService;
    }

    //create
    public Film addNewFilm(Film film) {
        checkValidation(film);
        return filmStorage.addNewFilm(film);
    }

    //read
    public Film getFilmById(Long id) {
        return filmStorage.getFilmByID(id);
    }


    private Collection<Film> getUsersFavouritesFilms(long userId) {
        Collection<Film> userFavouritesFilms = new HashSet<>();
        for (Film film : getAllFilms()) {
            if (film.getLikes().contains(userId)) {
                userFavouritesFilms.add(film);
            }
        }
        return userFavouritesFilms;
    }

    public Collection<Film> getRecommendations(Long userId) {
        log.info("Получить список рекомендаций для пользователя с id = {}", userId);
        Collection<User> users = new HashSet<>(userDbService.getAllUser());
        users.remove(userDbService.getUserById(userId));
        Collection<Film> currentUserFilms = getUsersFavouritesFilms(userId);
        long maxSimilar = 0;
        long maxNotSimilar = 0;
        long maxSimilarUserId = -1;
        for (User user : users) {
            Collection<Film> tempFilms = new HashSet<>(currentUserFilms);
            tempFilms.retainAll(getUsersFavouritesFilms(user.getId()));
            long currentSimilar = tempFilms.size();
            tempFilms = new HashSet<>(getUsersFavouritesFilms(user.getId()));
            tempFilms.removeAll(currentUserFilms);
            long currentNotSimilar = tempFilms.size();
            if (currentSimilar > maxSimilar) {
                maxSimilar = currentSimilar;
                maxNotSimilar = currentNotSimilar;
                maxSimilarUserId = user.getId();
            } else if (currentSimilar == maxSimilar && currentNotSimilar > maxNotSimilar && currentSimilar > 0) {
                maxNotSimilar = currentNotSimilar;
                maxSimilarUserId = user.getId();
            }
        }
        if (maxSimilarUserId == -1) {
            return new ArrayList<>();
        }
        Collection<Film> recommendedFilms = getUsersFavouritesFilms(maxSimilarUserId);
        recommendedFilms.removeAll(currentUserFilms);
        return recommendedFilms;
    }

    public List<Film> getAllFilms() {
        return filmStorage.allFilms();
    }

    public List<Film> getMostPopular(Long count, Long genreId, Integer year) {
        return filmStorage.getMostPopular(count, genreId, year);
    }

    public List<Film> getFilmBySort(Long id, List<String> sortBy) {
        return filmStorage.getFilmBySort(id, sortBy);
    }

    //update
    public Film updateFilm(Film film) {
        checkValidation(film);
        return filmStorage.update(film);
    }

    public void takeLike(Long id, Long userId) {
        userDbService.getUserById(userId);
        filmStorage.takeLike(id, userId);
    }

    //delete
    public void deleteLike(Long id, Long userId) {
        userDbService.getUserById(userId);
        filmStorage.deleteLike(id, userId);
    }

    public List<Film> search(String query, String by) {
        log.info("Запрос на поиск фильма по названию и/или по режиссёру");
        if (!by.equals("director") && !by.equals("title")
                && !by.equals("director,title") && !by.equals("title,director")) {
            log.error("Параметры запроса переданы неверно");
            throw new NotFoundException("Параметры запроса переданы неверно");
        }

        List<Film> filteredFilms;
        if (by.equals("director")) {
            log.info("Поиск фильма по режиссёру");
            filteredFilms = filmStorage.allFilms()
                    .stream().filter(film -> film.getDirectors().stream()
                    .anyMatch(director -> director.getName().contains(query))).toList();
        } else if (by.equals("title")) {
            log.info("Поиск фильма по названию");
            filteredFilms = filmStorage.allFilms().stream()
                    .filter(film -> film.getName().contains(query)).toList();
        } else {
            log.info("Поиск фильма по названию и по режиссёру");
            filteredFilms = filmStorage.allFilms()
                    .stream().filter(film -> film.getName().contains(query) ||
                            film.getDirectors().stream()
                                    .anyMatch(director -> director.getName().contains(query))).toList();
        }
        return filteredFilms;
    }


    public void deleteFilmById(Long id) {
        filmStorage.deleteFilmByID(id);
    }

    public List<Film> getCommonFilms(long userId, long friendId) {
        userDbService.getUserById(userId);
        userDbService.getUserById(friendId);
        return filmStorage.getCommonFilms(userId, friendId);
    }

    private void checkValidation(Film film) {
        if (film.getName().isBlank()) {
            log.error("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } else if ((film.getReleaseDate().isBefore(LocalDate.parse("28.12.1895", formatter)))) {
            log.error("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() < 0) {
            log.error("Продолжительность не может быть отрицательной");
            throw new ValidationException("Продолжительность не может быть отрицательной");
        }
    }
}
