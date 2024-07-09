package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private FilmService filmService;

    @Autowired
    public FilmController (FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public void addFilm(@RequestBody Film film) {
        filmService.addNewFilm(film);
    }

    @GetMapping
    public Collection<Film> allFilm() {
        return filmService.allFilm();
    }

    @GetMapping("/{id}")
    public Film getFilmByID(@PathVariable Long id) {
        return filmService.getFilmByID(id);
    }

    @PutMapping
    public void update(@RequestBody Film film) {
        filmService.update(film);
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilm(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void takeLike(@PathVariable("id") Long filmId,
                         @PathVariable Long userId) {
        filmService.takeLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Long filmId,
                           @PathVariable Long userId) {
        filmService.deleteLike(filmId, userId);

    }
}
