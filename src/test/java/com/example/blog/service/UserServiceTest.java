package com.example.blog.service;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.BlogService;
import com.example.blog.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUser_UserDoesNotExist_ShouldSaveUser() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        User result = userService.addUser(user);

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
        Assertions.assertEquals(user, result);
    }

    @Test
    public void testAddUser_UserAlreadyExist_ShouldNotSaveUser() {
        User existingUser = new User();
        existingUser.setUsername("existingUser");

        User user = new User();
        user.setUsername("existingUser");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(existingUser);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void testGetUsers_ReturnsAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("user1");
        user1.setEmail("user1");

        User user2 = new User();
        user1.setId(2L);
        user1.setUsername("user2");
        user1.setPassword("user2");
        user1.setEmail("user2");

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        Assertions.assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetBlogs_ReturnsEmptyListWhenNoBlogsExist() {
        List<User> users = Collections.emptyList();

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        Assertions.assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindUser_WhenUserDoesNotExist_ShouldThrowIllegalArgumentException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.findUser(1L);
        });
    }

    @Test
    public void testFindUser_WhenUserExist_ShouldFindUser() {
        User user = new User();
        user.setId(2L);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        User result = userService.findUser(2L);

        Assertions.assertEquals(user, result);
    }

    @Test
    public void testDeleteUser_WhenUserExists_ShouldDeleteAndReturnUser() {
        User userToDelete = new User();
        userToDelete.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userToDelete));

        User deletedUser = userService.deleteUser(1L);

        Assertions.assertEquals(userToDelete, deletedUser);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1L);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    public void testChangeUserEmail_WhenUserExists_ShouldChangeAndReturnUser() {
        Long userId = 1L;
        String newEmail = "newemail@example.com";
        User existingUser = new User();
        existingUser.setId(userId);

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setEmail(newEmail);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.changeUserEmail(userId, updatedUser);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
        Assertions.assertEquals(updatedUser, result);
        Assertions.assertEquals(newEmail, result.getEmail());
    }

    @Test
    public void testChangeUserEmail_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        Long userId = 1L;
        User userToUpdate = new User();
        userToUpdate.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.changeUserEmail(userId, userToUpdate);
        });

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testAddSubscriber_WhenUserAndBlogExist_ShouldAddSubscriberAndReturnBlog() {
        Long userId = 1L;
        Long blogId = 2L;

        User existingUser = new User();
        existingUser.setId(userId);

        Blog existingBlog = new Blog();
        existingBlog.setId(blogId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(blogRepository.findById(blogId)).thenReturn(Optional.of(existingBlog));

        Blog result = userService.addSubscriber(userId, blogId);

        verify(userRepository, times(1)).findById(userId);
        verify(blogRepository, times(1)).findById(blogId);
        verify(userRepository, times(1)).save(existingUser);
        verify(blogRepository, times(1)).save(existingBlog);

        Assertions.assertTrue(existingUser.getSubscriptions().contains(existingBlog));
        Assertions.assertTrue(existingBlog.getSubscribers().contains(existingUser));
        Assertions.assertEquals(existingBlog, result);
    }

    @Test
    public void testAddSubscriber_WhenUserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        Long blogId = 2L;

        Blog existingBlog = new Blog();
        existingBlog.setId(blogId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.addSubscriber(userId, blogId);
        });

        verify(userRepository, times(1)).findById(userId);
        verify(blogRepository, never()).findById(blogId);
        verify(userRepository, never()).save(any(User.class));
        verify(blogRepository, never()).save(any(Blog.class));
    }

    @Test
    public void testAddSubscriber_WhenBlogDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        Long blogId = 2L;

        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.addSubscriber(userId, blogId);
        });

        verify(userRepository, times(1)).findById(userId);
        verify(blogRepository, times(1)).findById(blogId);
        verify(userRepository, never()).save(any(User.class));
        verify(blogRepository, never()).save(any(Blog.class));
    }
}
