package com.example.blog.service;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
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
        String newEmail = "newemail@example.com";
        User existingUser = new User();
        existingUser.setId(1L);

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail(newEmail);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.changeUserEmail(1L, updatedUser);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
        Assertions.assertEquals(updatedUser, result);
        Assertions.assertEquals(newEmail, result.getEmail());
    }

    @Test
    public void testChangeUserEmail_WhenUserDoesNotExist_ShouldThrowException() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.changeUserEmail(1L, userToUpdate);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testAddSubscriber_WhenUserAndBlogExist_ShouldAddSubscriberAndReturnBlog() {
        User existingUser = new User();
        existingUser.setId(1L);

        Blog existingBlog = new Blog();
        existingBlog.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(blogRepository.findById(1L)).thenReturn(Optional.of(existingBlog));

        Blog result = userService.addSubscriber(1L, 1L);

        verify(userRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
        verify(blogRepository, times(1)).save(existingBlog);

        Assertions.assertTrue(existingUser.getSubscriptions().contains(existingBlog));
        Assertions.assertTrue(existingBlog.getSubscribers().contains(existingUser));
        Assertions.assertEquals(existingBlog, result);
    }

    @Test
    public void testAddSubscriber_WhenUserDoesNotExist_ShouldThrowException() {
        Blog existingBlog = new Blog();
        existingBlog.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.addSubscriber(1L, 1L);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(blogRepository, never()).findById(1L);
        verify(userRepository, never()).save(any(User.class));
        verify(blogRepository, never()).save(any(Blog.class));
    }

    @Test
    public void testAddSubscriber_WhenBlogDoesNotExist_ShouldThrowException() {
        User existingUser = new User();
        existingUser.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.addSubscriber(1L, 1L);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
        verify(blogRepository, never()).save(any(Blog.class));
    }

    @Test
    public void testGetAuthorBlogs_WhenUserExists_ShouldReturnBlogs() {
        User user = new User();
        List<Blog> expectedBlogs = new ArrayList<>();
        expectedBlogs.add(new Blog());
        expectedBlogs.add(new Blog());
        user.setBlogs(expectedBlogs);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Blog> actualBlogs = userService.getAuthorBlogs(1L);

        Assertions.assertEquals(expectedBlogs, actualBlogs);
    }

    @Test
    public void testGetAuthorBlogs_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getAuthorBlogs(1L);
        });
    }

    @Test
    public void testGetSubscriptions_WhenUserExists_ShouldReturnSubscriptions() {
        User user = new User();
        Set<Blog> expectedSubscriptions = new HashSet<>();
        expectedSubscriptions.add(new Blog());
        expectedSubscriptions.add(new Blog());
        user.setSubscriptions(expectedSubscriptions);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<Blog> actualSubscriptions = userService.getSubscriptions(1L);

        Assertions.assertEquals(expectedSubscriptions, actualSubscriptions);
    }

    @Test
    public void testGetSubscriptions_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getSubscriptions(1L);
        });
    }

    @Test
    public void testUnsubscribe_WhenUserAndBlogExist_ShouldReturnUpdatedSubscriptions() {
        User user = new User();
        user.setId(1L);
        Set<Blog> userSubscriptions = new HashSet<>();
        Blog blog = new Blog();
        blog.setId(1L);

        userSubscriptions.add(blog);
        user.setSubscriptions(userSubscriptions);

        Set<User> blogSubscribers = new HashSet<>();
        blogSubscribers.add(user);
        blog.setSubscribers(blogSubscribers);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));

        Set<Blog> updatedSubscriptions = userService.unsubscribe(1L, 1L);

        Assertions.assertEquals(userSubscriptions, updatedSubscriptions);
        Assertions.assertFalse(user.getSubscriptions().contains(blog));
        Assertions.assertFalse(blog.getSubscribers().contains(user));
    }

    @Test
    public void testUnsubscribe_WhenUserNotFound_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.unsubscribe(1L, 1L);
        });
    }

    @Test
    public void testUnsubscribe_WhenBlogNotFound_ShouldThrowException() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.unsubscribe(1L, 1L);
        });
    }

    @Test
    public void testGetAuthor_WhenAuthorsExist_ShouldReturnAuthors() {
        List<User> expectedAuthors = new ArrayList<>();
        User author1 = new User();
        author1.setId(1L);
        author1.setUsername("testAuthor1");
        expectedAuthors.add(author1);

        User author2 = new User();
        author2.setId(2L);
        author2.setUsername("testAuthor2");
        expectedAuthors.add(author2);

        when(userRepository.findAllAuthors()).thenReturn(expectedAuthors);

        List<User> actualAuthors = userService.getAuthor();

        Assertions.assertEquals(expectedAuthors, actualAuthors);
    }

    @Test
    public void testGetAuthor_WhenNoAuthorsExist_ShouldReturnEmptyList() {
        List<User> expectedAuthors = new ArrayList<>();
        when(userRepository.findAllAuthors()).thenReturn(expectedAuthors);

        List<User> actualAuthors = userService.getAuthor();

        Assertions.assertEquals(expectedAuthors, actualAuthors);
    }
}
