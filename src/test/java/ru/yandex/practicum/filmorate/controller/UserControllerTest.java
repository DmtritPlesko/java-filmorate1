package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Autowired
    private UserController controller;

    @Test
    public void addNewUser() {
        User user = User.builder()
                .login("Dima")
                .email("yandex@mail.ru")
                .birthday(LocalDate.parse("12.05.2003", formatter))
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
                .birthday(LocalDate.parse("12.05.2003", formatter))
                .build();

        controller.update(user);
        assertNotNull(controller.allUsers());

        assertNotEquals(user, user1);
    }

    @Test
    public void getAllUsers() {
        assertNotNull(controller.allUsers());
    }

}