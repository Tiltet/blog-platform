package com.example.blog.controllers;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
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

    @Test
    void testGetUsers_ReturnsUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        users.add(user1);
        users.add(user2);

        when(userService.getUsers()).thenReturn(users);

        List<User> actualUsers = userController.getUsers();

        assertEquals(users, actualUsers);

        verify(userService, times(1)).getUsers();
    }

    @Test
    void testGetBlogs_ReturnsEmptyList() {
        when(userService.getUsers()).thenReturn(Collections.emptyList());

        List<User> actualBlogs = userController.getUsers();

        assertTrue(actualBlogs.isEmpty());

        verify(userService, times(1)).getUsers();
    }

    @Test
    void testAddUser_ReturnsAddedUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        when(userService.addUser(user)).thenReturn(user);

        User addedUser = userController.addUser(user);

        assertEquals(user, addedUser);

        verify(userService, times(1)).addUser(user);
    }

    @Test
    void testAddUser_WithNullUser_ReturnsNull() {
        User user = null;
        when(userService.addUser(user)).thenReturn(null);

        User addedUser = userController.addUser(user);

        assertNull(addedUser);

        verify(userService, times(1)).addUser(user);
    }

    @Test
    void testFindUserById_ReturnsUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(userService.findUser(1L)).thenReturn(user);

        User foundUser = userController.findUserById(1L);

        assertEquals(user, foundUser);

        verify(userService, times(1)).findUser(1L);
    }

    @Test
    void testFindUserById_WithNonExistingUser_ReturnsNull() {
        when(userService.findUser(1L)).thenReturn(null);

        User foundUser = userController.findUserById(1L);

        assertNull(foundUser);

        verify(userService, times(1)).findUser(1L);
    }

    @Test
    void testDeleteUser_ReturnsDeletedUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(userService.deleteUser(1L)).thenReturn(user);

        User result = userController.deleteUser(1L);

        assertEquals(user, result);

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_WithNonExistingUser_ReturnsNull() {
        when(userService.deleteUser(1L)).thenReturn(null);

        User result = userController.deleteUser(1L);

        assertNull(result);

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testChangeUserEmail_ReturnsModifiedUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setEmail("mail.ru");

        User modifiedUser = new User();
        modifiedUser.setId(1L);
        modifiedUser.setEmail("yandex.ru");

        when(userService.changeUserEmail(1L, user)).thenReturn(modifiedUser);

        User result = userController.changeUserEmail(1L, user);

        assertEquals(modifiedUser, result);

        verify(userService, times(1)).changeUserEmail(1L, user);
    }

    @Test
    void testChangeUserEmail_WithNonExistingUser_ReturnsNull() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(userService.changeUserEmail(1L, user)).thenReturn(null);

        User result = userController.changeUserEmail(1L, user);

        assertNull(result);

        verify(userService, times(1)).changeUserEmail(1L, user);
    }

    @Test
    void testAddSubscriber_ReturnsModifiedBlog() {

        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("blog");
        blog.setDescription("description");

        Blog modifiedBlog = new Blog();
        modifiedBlog.setId(1L);
        modifiedBlog.setTitle("blog");
        modifiedBlog.setDescription("description");

        when(userService.addSubscriber(1L, 1L)).thenReturn(modifiedBlog);

        Blog result = userController.addSubscriber(1L, 1L);

        assertEquals(modifiedBlog, result);

        verify(userService, times(1)).addSubscriber(1L, 1L);
    }

    @Test
    void testAddSubscriber_WithNonExistingBlog_ReturnsNull() {
        when(userService.addSubscriber(1L, 1L)).thenReturn(null);

        Blog result = userController.addSubscriber(1L, 1L);

        assertNull(result);

        verify(userService, times(1)).addSubscriber(1L, 1L);
    }

    @Test
    void testGetAuthorBlogs_ReturnsListOfBlogs() {
        List<Blog> expectedBlogs = Arrays.asList(
                createBlog(1L, "Blog 1", "Content 1"),
                createBlog(2L, "Blog 2", "Content 2"),
                createBlog(3L, "Blog 3", "Content 3")
        );
        when(userService.getAuthorBlogs(1L)).thenReturn(expectedBlogs);

        List<Blog> result = userController.getAuthorBlogs(1L);

        assertEquals(expectedBlogs, result);

        verify(userService, times(1)).getAuthorBlogs(1L);
    }

    @Test
    void testGetAuthorBlogs_WithNonExistingUser_ReturnsEmptyList() {
        when(userService.getAuthorBlogs(1L)).thenReturn(Collections.emptyList());

        List<Blog> result = userController.getAuthorBlogs(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userService, times(1)).getAuthorBlogs(1L);
    }

    @Test
    void testGetSubscriptions_ReturnsSetOfBlogs() {
        Set<Blog> expectedSubscriptions = new HashSet<>(Arrays.asList(
                createBlog(1L, "Blog 1", "Content 1"),
                createBlog(2L, "Blog 2", "Content 2"),
                createBlog(3L, "Blog 3", "Content 3")
        ));
        when(userService.getSubscriptions(1L)).thenReturn(expectedSubscriptions);

        Set<Blog> result = userController.getSubscriptions(1L);

        assertEquals(expectedSubscriptions, result);

        verify(userService, times(1)).getSubscriptions(1L);
    }

    @Test
    void testGetSubscriptions_WithNonExistingUser_ReturnsEmptySet() {
        when(userService.getSubscriptions(1L)).thenReturn(Collections.emptySet());

        Set<Blog> result = userController.getSubscriptions(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userService, times(1)).getSubscriptions(1L);
    }

    @Test
    void testUnsubscribe_ReturnsUpdatedSubscriptions() {
        Set<Blog> expectedSubscriptions = new HashSet<>(Arrays.asList(
                createBlog(2L, "Blog 2", "Content 2"),
                createBlog(3L, "Blog 3", "Content 3")
        ));
        when(userService.unsubscribe(1L, 1L)).thenReturn(expectedSubscriptions);

        Set<Blog> result = userController.unsubscribe(1L, 1L);

        assertEquals(expectedSubscriptions, result);

        verify(userService, times(1)).unsubscribe(1L, 1L);
    }

    @Test
    void testUnsubscribe_WithNonExistingSubscription_ReturnsUnchangedSubscriptions() {
        Set<Blog> subscriptions = new HashSet<>(Arrays.asList(
                createBlog(2L, "Blog 2", "Content 2"),
                createBlog(3L, "Blog 3", "Content 3")
        ));
        when(userService.unsubscribe(1L, 1L)).thenReturn(subscriptions);

        Set<Blog> result = userController.unsubscribe(1L, 1L);

        assertEquals(subscriptions, result);

        verify(userService, times(1)).unsubscribe(1L, 1L);
    }

    @Test
    void testGetAuthors_ReturnsListOfAuthors() {

        List<User> expectedAuthors = Arrays.asList(
                createUser(1L, "John"),
                createUser(2L, "Alice"),
                createUser(3L, "Bob")
        );
        when(userService.getAuthor()).thenReturn(expectedAuthors);

        List<User> result = userController.getAuthor();

        assertEquals(expectedAuthors, result);

        verify(userService, times(1)).getAuthor();
    }

    @Test
    void testGetAuthors_WithNoAuthors_ReturnsEmptyList() {
        when(userService.getAuthor()).thenReturn(Collections.emptyList());

        List<User> result = userController.getAuthor();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userService, times(1)).getAuthor();
    }

    private Blog createBlog(Long id, String title, String description) {
        Blog blog = new Blog();
        blog.setId(id);
        blog.setTitle(title);
        blog.setDescription(description);
        return blog;
    }

    private User createUser(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        return user;
    }
}
