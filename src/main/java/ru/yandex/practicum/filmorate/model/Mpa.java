package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
public class Mpa {

    private int id;

    private String name;

    public Mpa(int id) {
        this.id = id;
    }

    public Mpa(int id, String ratingName) {
        this.id = id;
        this.name = ratingName;
    }
}
