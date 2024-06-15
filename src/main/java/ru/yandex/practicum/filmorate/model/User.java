package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    private Long id;

    @NonNull
    @EqualsAndHashCode.Include
    private String email;

    @NonNull
    @EqualsAndHashCode.Include
    private String login;

    private String name;

    private String birthday;

}
