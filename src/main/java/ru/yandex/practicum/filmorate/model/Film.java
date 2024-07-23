package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.enums.Rating;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder
@NonNull
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    String name;

    String description;

    LocalDate releaseDate;

    long id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    Duration duration;

    Set<Long> likes;

    Set<String> genres;

    Rating rating;
}


