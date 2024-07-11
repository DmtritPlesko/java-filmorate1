package ru.yandex.practicum.filmorate.storage.filmstorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;

import java.util.*;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorageInterface {
    private final Map<Long, Film> filmMap = new HashMap<>();

    @Override
    public Film addNewFilm(Film film) {
        film.setId(getNextId());
        film.setLikes(new HashSet<>());
        filmMap.put(film.getId(), film);
        log.info("Фильм {} добалвен в коллекцию", film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {

        if(filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("Данные о фильме {} обновлены ", film.getName());
            return film;
        } else {
            log.error("Невозможно обновить фильм с id = {}", film.getId());
            throw new NotFoundException("Невозможно обновить фильм с id = " + film.getId());
        }
    }

    public Collection<Film> allFilms() {
        log.info("Всего фильмов: {}", filmMap.values().size());
        return filmMap.values();
    }

    public Film getFilmByID(Long id) {
        if (filmMap.containsKey(id)) {
            log.info("Фильм с id = {} успешно найден", id);
            return filmMap.get(id);
        } else {
            log.error("Фильм с id = {}  не существует в системе", id);
            throw new NotFoundException("Фильм с id = " + id + " не существует в системе");
        }
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
