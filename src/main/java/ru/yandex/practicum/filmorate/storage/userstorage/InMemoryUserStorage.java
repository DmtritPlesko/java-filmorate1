package ru.yandex.practicum.filmorate.storage.userstorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.User;

import java.util.*;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorageInterface {

    private Map<Long, User> userMap = new HashMap<>();

    public void createUser(User user) {
        user.setId(getNextId());
        userMap.put(user.getId(), user);
        log.info("Пользователь с именем - {} и id - {} ", user.getName(), user.getId() + " создан");
    }


    public void update(User user) {
        log.info("Данные пользователя с идентефикатором и именем {} {} "
                , user.getId(), user.getName() + " обновлены");
        userMap.put(user.getId(), user);
    }

    public Collection<User> allUser() {
        log.info("Всего пользователей: {} ", userMap.values().size());
        return userMap.values();
    }

    public User getUserById(Long id) {
        return userMap.get(id);
    }

    public void addNewFriend(Long userId, Long friendId) {
        getUserById(userId).getFriends().add(getUserById(friendId));
        getUserById(friendId).getFriends().add(getUserById(userId));
        log.info("Пользователь с id = {} добавил в друзья пользователя с id = {} ", userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        getUserById(userId).getFriends().remove(friendId);
        getUserById(friendId).getFriends().remove(userId);
        log.info("Пользователь с id = {} удалил из друзей пользователя с id = {} ", userId, friendId);

    }

    public Set<User> allFriend(Long userId) {
        return getUserById(userId).getFriends();
    }

    public List<User> getMutualFriends(Long userId, Long friendId) {
        Set<User> friendUser = getUserById(userId).getFriends();
        Set<User> friend = getUserById(friendId).getFriends();

        return friend.stream().filter(friendUser::contains).toList();
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
