package com.example.blog.service;

import com.example.blog.entities.User;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testAddUser_WhenUserDoesNotExist_ShouldSaveUser() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        User result = userService.addUser(user);

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
        Assertions.assertEquals(user, result);
    }

    @Test
    public void testAddUser_WhenUserAlreadyExist_ShouldNotSaveUser() {
        User existingUser = new User();
        existingUser.setUsername("existingUser");

        User user = new User();
        user.setUsername("existingUser");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(existingUser);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }
}
