package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private Map<Long, User> userMap = new HashMap<>();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @PostMapping
    public void addNewUser(@RequestBody User user) {

        try {

            checkValidation(user);

        } catch (ValidationException e) {
            log.error("Ошибка валидации: {} ", e.getMessage());
            return;
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        userMap.put(user.getId(), user);

        log.info("Пользователь с именем: {} ", user.getName() + " создан");
    }


    @PutMapping
    public void update(@RequestBody User user) {

        if (!userMap.containsKey(user.getId())) {
            log.error("Пользователь с идентефикатором: {} ", user.getId() + " не найден");
            return;
        }

        checkValidation(user);

        log.info("Данные пользователя с идентефикатором и именем {} {} "
                , user.getId(), user.getName() + " обновлены");
        userMap.put(user.getId(), user);
    }


    @GetMapping
    public Collection<User> allUser() {
        log.info("Всего пользователей: {} ", userMap.values().size());
        return userMap.values();
    }

    public User getUserById(Long id) {
        return userMap.get(id);
    }

    private long getNextId() {
        long currentMaxId = userMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private void checkValidation(User user) throws ValidationException {
        if (user.getEmail().isBlank()) {
            log.error("Электронная почта не может быть пустой");
            throw new ValidationException("Электронная почта не может быть пустой");

        } else if (!Pattern.matches("^[a-zA-Z0-9]+@+[a-z]+.+[a-z]$", user.getEmail())) {
            log.error("Неверный формат почты");
            throw new ValidationException("Неверный формат почты");

        } else if (user.getLogin().isBlank()) {
            log.error("Логин не может быть пустым");
            throw new ValidationException("Логин не может быть пустым");

        } else if (user.getLogin().contains(" ")) {
            log.error("Логин должен быть без пробелов");
            throw new ValidationException("Логин должен быть без пробелов");

        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть в будущем.");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}
