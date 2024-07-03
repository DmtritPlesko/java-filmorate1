package ru.yandex.practicum.filmorate.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {

    @NonNull
    String name;

    @NonNull
    String description;

    LocalDate releaseDate;

    @EqualsAndHashCode.Exclude
    long id;

    Duration duration;

    Set<Long> likes = new HashSet<>();

}


