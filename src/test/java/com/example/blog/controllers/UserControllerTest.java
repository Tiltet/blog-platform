package com.example.blog.controllers;

import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private UserService userService = new UserService(userRepository, blogRepository);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
}
