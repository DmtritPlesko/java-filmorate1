package ru.yandex.practicum.filmorate.storage.dao.directorDb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectorDbStorage implements DirectorDb {

    @Override
    public List<Director> allDirectors() {
        return List.of();
    }

    @Override
    public Director createNewDirector(Director director) {
        return null;
    }

    @Override
    public Director getDirectorById(Long id) {
        return null;
    }

    @Override
    public Director updateDirector(Director director) {
        return null;
    }

    @Override
    public void deleteDirectorById(Long id) {

    }
}
