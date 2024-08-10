package ru.yandex.practicum.filmorate.storage.dao.userDb;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser();
    }

    private User createUser() {
        String uniqueEmailSuffix = UUID.randomUUID().toString();
        String email = "email" + uniqueEmailSuffix + "@mail.ru";

        User user = new User();
        user.setEmail(email);
        user.setName("Имя");
        user.setBirthday(LocalDate.now());
        return userDbStorage.createUser(user);
    }

    @Test
    public void checkCreateNewUserAndGetById() {

        User user1 = userDbStorage.getUserById(user.getId());

        assertThat(user1).hasFieldOrPropertyWithValue("id", user.getId());


    }

    @Test
    public void checkGetAllUsers() {

        List<User> users = userDbStorage.allUser();

        Assertions.assertNotNull(users.size(), "Список юзеров пуст");

    }

    @Test
    public void updateUserAndGetById() {

        user.setName("Rita");
        userDbStorage.update(user);

        User user1 = userDbStorage.getUserById(user.getId());

        assertThat(user1).hasFieldOrPropertyWithValue("name", "Rita");


    }

    @Test
    public void compareUsers() {
        User user1 = createUser();
        user1.setEmail(user.getEmail());

        Assertions.assertNotEquals(user1, user);

    }

}