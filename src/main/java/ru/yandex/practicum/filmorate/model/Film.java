package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    private long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    private LocalDate releaseDate;

    private Duration duration;
}
