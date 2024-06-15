package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.User;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController controller;

    @Test
    public void addNewUser() {
        User user = User.builder()
                .login("Dima")
                .email("yandex@mail.ru")
                .birthday("12.05.2010")
                .build();

        controller.addNewUser(user);

        assertEquals(1, user.getId());
    }

    @Test
    public void updateUser() {
        long id = 1;
        User user1 = controller.getUserById(id);

        User user = User.builder()
                .id(id)
                .name("Dima")
                .email("dimka@icloud.com")
                .login("Hello")
                .birthday("12.05.2003")
                .build();

        controller.update(user);
        assertNotNull(controller.allUser());

        assertNotEquals(user, user1);
    }

    @Test
    public void getAllUsers() {
        assertNotNull(controller.allUser());
    }

}