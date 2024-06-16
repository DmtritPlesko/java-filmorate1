package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private Map<Long, Film> filmMap = new HashMap<>();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @PostMapping
    public void addNewFilm(@RequestBody Film film) {


        checkValidation(film);

        film.setId(getNextId());
        filmMap.put(film.getId(), film);

        log.info("Фильм {} добалвен в коллекцию", film.getName());
    }

    @PutMapping
    public void update(@RequestBody Film film) {

        if (!filmMap.containsKey(film.getId())) {
            log.error("Фильм с идентефикатором : {}", film.getId() + " не найден");
            return;
        }

        try {
            checkValidation(film);
        } catch (ValidationException e) {
            log.error("Ошибка валидации: {} ", e.getMessage());
            return;
        }

        filmMap.put(film.getId(), film);
        log.info("Данные о фильм {} обновлены ", film.getName());
    }


    @GetMapping
    public Collection<Film> allFilms() {
        log.info("Всего фильмов: {}", filmMap.values().size());
        return filmMap.values();
    }

    private long getNextId() {
        long currentMaxId = filmMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
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
        } else if (film.getDuration().getSeconds()<0) {
            log.error("Продолжительность не может быть отрицательной");
            throw new ValidationException("Продолжительность не может быть отрицательной");
        }

    }
}
