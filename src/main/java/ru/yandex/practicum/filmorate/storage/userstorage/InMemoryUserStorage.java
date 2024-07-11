package ru.yandex.practicum.filmorate.storage.userstorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorageInterface {

    private final Map<Long, User> userMap = new HashMap<>();

    public User createUser(User user) {
        user.setId(getNextId());
        user.setFriends(new HashSet<>());
        userMap.put(user.getId(), user);
        log.info("Пользователь с именем - {} и id - {} ", user.getName(), user.getId() + " создан");
        return user;
    }


    public User update(User user) {

        if(userMap.containsKey(user.getId())) {
            log.info("Данные пользователя с идентефикатором и именем {} {} "
                    , user.getId(), user.getName() + " обновлены");
            user.setFriends(new HashSet<>());
            userMap.put(user.getId(), user);
        } else {
            log.error("Невозможно обновить пользователя с id = " + user.getId());
            throw new NotFoundException("Невозможно обновить пользователя с id = " + user.getId());
        }
        return user;
    }

    public Collection<User> allUser() {
        log.info("Всего пользователей: {} ", userMap.values().size());
        return userMap.values();
    }

    public User getUserById(Long id) {
        if (userMap.containsKey(id)) {
            log.info("Пользователь с id = {} успешно найден",id);
            return userMap.get(id);
        } else {
            log.error("Пользователь с id = {} не существует",id);
            throw new NotFoundException("Пользователь с id = " + id + " не существует");
        }
    }

    public void addNewFriend(Long userId, Long friendId) {

        getUserById(userId).getFriends().add(friendId);
        getUserById(friendId).getFriends().add(userId);

        log.info("Пользователь с id = {} добавил в друзья пользователя с id = {} ", userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        getUserById(userId).getFriends().remove(friendId);
        getUserById(friendId).getFriends().remove(userId);
        log.info("Пользователь с id = {} удалил из друзей пользователя с id = {} ", userId, friendId);

    }

    public Set<User> allFriend(Long userId) {
        return getUserById(userId).getFriends().stream()
                .map(userMap::get).collect(Collectors.toSet());
    }

    public List<User> getMutualFriends(Long userId, Long friendId) {
        Set<Long> friendUser = getUserById(userId).getFriends();
        Set<Long> friend = getUserById(friendId).getFriends();

        return friend.stream()
                .filter(friendUser::contains)
                .map(userMap::get)
                .toList();
    }

    private long getNextId() {
        long currentMaxId = userMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
