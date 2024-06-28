package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class FilmControllerTest {
    @Autowired
    private FilmController controller;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Test
    public void addUser() {
        Film film = Film.builder()
                .description("Какое то описание")
                .name("Silent Hill")
                .releaseDate(LocalDate.parse("12.05.2003", formatter))
                .duration(Duration.ofMinutes(90))
                .build();


        controller.addNewFilm(film);
        assertEquals(1, film.getId());
    }

    @Test
    public void getAllFilms() {
        assertNotNull(controller.allFilms());
    }
}