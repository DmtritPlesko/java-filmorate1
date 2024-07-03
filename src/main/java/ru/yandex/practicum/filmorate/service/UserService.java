package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.storage.userstorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.userstorage.UserStorageInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


@Slf4j
@Service
public class UserService {
    private UserStorageInterface userStorage;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    public UserService(UserStorageInterface userStorage) {
        this.userStorage = userStorage;
    }

    public void createUser(User user) {
        checkValidation(user);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        userStorage.createUser(user);
    }

    public void update(User user) {
        if (userStorage.getUserById(user.getId()) == null) {
            log.error("Пользователь с идентефикатором: {} ", user.getId() + " не найден");
            throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
        }

        checkValidation(user);

        userStorage.update(user);
    }

    public User getUserById(Long userId) {
        containsUser(userId);

        return userStorage.getUserById(userId);
    }

    public Collection<User> allUser() {
        return userStorage.allUser();
    }

    public void addNewFriend(Long userId, Long friendId) {
        containsUser(userId);
        containsUser(friendId);

        userStorage.addNewFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        containsUser(userId);
        containsUser(friendId);

        userStorage.deleteFriend(userId, friendId);
    }

    public Set<User> getAllFriend(Long userId) {
        containsUser(userId);

        return userStorage.allFriend(userId);
    }

    public List<User> getMutualFriends(Long userId, Long friendId) {
        containsUser(userId);
        containsUser(friendId);

        return userStorage.getMutualFriends(userId, friendId);
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

    private void containsUser(Long userId) {
        if (userStorage.getUserById(userId) == null) {
            log.error("Пользователь с id = {} не найден", userId);
            throw new NotFoundException("Пользователя с id =  " + userId
                    + " нет в системе");
        }
    }

}
