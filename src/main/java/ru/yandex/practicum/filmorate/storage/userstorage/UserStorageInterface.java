package ru.yandex.practicum.filmorate.storage.userstorage;

import ru.yandex.practicum.filmorate.dto.User;

import java.util.Collection;

import java.util.List;
import java.util.Set;

public interface UserStorageInterface {

    void createUser(User user);

    void update(User user);

    Collection<User> allUser();

    void addNewFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    User getUserById(Long id);

    Set<User> allFriend(Long userId);

    List<User> getMutualFriends(Long userId, Long friendId);
}
