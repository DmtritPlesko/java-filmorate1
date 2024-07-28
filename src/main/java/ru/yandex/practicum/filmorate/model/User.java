package ru.yandex.practicum.filmorate.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NonNull
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    Long id;

    String email;

    String login;

    String name;

    int years;

    String password;

    LocalDate birthday;

//    Set<Long> friends;

    Status friend;

}
