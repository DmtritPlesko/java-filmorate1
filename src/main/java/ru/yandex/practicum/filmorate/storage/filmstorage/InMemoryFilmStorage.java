package ru.yandex.practicum.filmorate.storage.filmstorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.Film;

import java.util.*;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorageInterface {
    private Map<Long, Film> filmMap = new HashMap<>();

    @Override
    public void addNewFilm(Film film) {

        film.setId(getNextId());
        filmMap.put(film.getId(), film);
        log.info("Фильм {} добалвен в коллекцию", film.getName());
    }

    @Override
    public void update(Film film) {
        filmMap.put(film.getId(), film);
        log.info("Данные о фильм {} обновлены ", film.getName());
    }

    public Collection<Film> allFilms() {
        log.info("Всего фильмов: {}", filmMap.values().size());
        return filmMap.values();
    }

    public Film getFilmByID(Long id) {
        return filmMap.get(id);
    }

    public void takeLike(Long id, Long userId) {
        getFilmByID(id).getLikes().add(userId);
    }

    public void deleteLike(Long id, Long userId) {
        getFilmByID(id).getLikes().remove(userId);
    }

    private long getNextId() {
        long currentMaxId = filmMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
