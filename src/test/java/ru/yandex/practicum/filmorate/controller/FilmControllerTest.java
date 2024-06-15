package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class FilmControllerTest {
    @Autowired
    private FilmController controller;

    @Test
    public void addUser() {
        Film film = Film.builder()
                .description("Какое то описание")
                .name("Silent Hill")
                .releaseDate("12.10.1985")
                .duration("90")
                .build();


        controller.addNewFilm(film);
        assertEquals(1, film.getId());
    }

    @Test
    public void getAllFilms() {
        assertNotNull(controller.allFilms());
    }
}