package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    String name;

    String description;

    LocalDate releaseDate;

    Long id;

    //    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    Long duration;

    Set<Long> likes;

    Set<Genre> genres;

    Mpa mpa;
}


