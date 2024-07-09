package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void addNewUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping
    public void update(@RequestBody User user) {
        userService.update(user);
    }

    @GetMapping
    public Collection<User> allUsers() {
        return userService.allUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendsId}")
    public void addNewFriend(@PathVariable Long id, @PathVariable Long friendsId) {
        userService.addNewFriend(id, friendsId);
    }

    @DeleteMapping("/{id}/friends/{friendsId}")
    public void deleteFriend(@PathVariable Long userId, Long friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getAllFriend(@PathVariable Long userId) {
        return userService.getAllFriend(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable("id") Long userId,
                                       @PathVariable("otherId") Long friendId) {
        return userService.getMutualFriends(userId, friendId);
    }

}