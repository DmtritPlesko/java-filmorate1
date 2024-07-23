package ru.yandex.practicum.filmorate.service;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
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

    public User createUser(User user) {
        validation(user);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return userStorage.createUser(user);
    }

    public User update(User user) {
        validation(user);
        return userStorage.update(user);
    }

    public User getUserById(Long userId) {
        return userStorage.getUserById(userId);
    }

    public Collection<User> allUser() {
        return userStorage.allUser();
    }

    public void addNewFriend(Long userId, Long friendId) {
        userStorage.addNewFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        userStorage.deleteFriend(userId, friendId);
    }

    public Set<User> getAllFriend(Long userId) {
        return userStorage.allFriend(userId);
    }

    public List<User> getMutualFriends(Long userId, Long friendId) {
        return userStorage.getMutualFriends(userId, friendId);
    }
    private void validation(User user) {
        if (StringUtils.isBlank(user.getEmail())) {
            throw new ValidationException("Электронная почта не может быть пустой");
        } else if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", user.getEmail())) {
            throw new ValidationException("Неверный формат почты");
        } else if (StringUtils.isBlank(user.getLogin())) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        } else if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }

}
