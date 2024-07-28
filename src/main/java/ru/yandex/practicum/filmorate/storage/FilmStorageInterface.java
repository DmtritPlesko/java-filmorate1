package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmStorageInterface {

    Film addNewFilm(Film film);

    Film update(Film film);

    void deleteFilm (Long id);

    Film getFilmByID(Long id);

    Collection<Film> allFilms();

    void takeLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);

    List<Film> getPopularFilm(Long limit);
}
