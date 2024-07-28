package ru.yandex.practicum.filmorate.controller.memory;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import ru.yandex.practicum.filmorate.model.User;
//import ru.yandex.practicum.filmorate.service.memory.UserService;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
//
//    private UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping
//    public User addNewUser(@RequestBody User user) {
//        return userService.createUser(user);
//    }
//
//    @PutMapping
//    public User update(@RequestBody User user) {
//        return userService.update(user);
//    }
//
//    @GetMapping
//    public Collection<User> allUsers() {
//        return userService.allUser();
//    }
//
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable("id") Long id) {
//        return userService.getUserById(id);
//    }
//
//    @PutMapping("/{id}/friends/{friendsId}")
//    public void addNewFriend(@PathVariable("id") Long id,
//                             @PathVariable("friendsId") Long friendsId) {
//        userService.addNewFriend(id, friendsId);
//    }
//
//    @DeleteMapping("/{id}/friends/{friendsId}")
//    public void deleteFriend(@PathVariable("id") Long userId,
//                             @PathVariable("friendsId") Long friendId) {
//        userService.deleteFriend(userId, friendId);
//    }
//
//    @GetMapping("/{id}/friends")
//    public Set<User> getAllFriend(@PathVariable("id") Long userId) {
//        return userService.getAllFriend(userId);
//    }
//
//    @GetMapping("/{id}/friends/common/{otherId}")
//    public List<User> getMutualFriends(@PathVariable("id") Long userId,
//                                       @PathVariable("otherId") Long friendId) {
//        return userService.getMutualFriends(userId, friendId);
//    }

}