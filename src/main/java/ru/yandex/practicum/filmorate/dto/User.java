package ru.yandex.practicum.filmorate.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    Long id;

    @EqualsAndHashCode.Include
    @NonNull
    String email;

    @EqualsAndHashCode.Include
    @NonNull
    String login;

    String name;

    LocalDate birthday;

    Set<User> friends = new HashSet<>();
}
