package com.example.blog.controllers;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.services.UserService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/** JavaDoc COMMENT. */
@SuppressWarnings("checkstyle:Indentation")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User userEntity) {
        return userService.addUser(userEntity);
    }

    @GetMapping("/user")
    public User findUserById(@RequestParam Long id) {
        return userService.findUser(id);
    }

    @DeleteMapping("/deleteUser")
    public User deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }

    @PatchMapping("/user")
    public User changeUserEmail(@RequestParam Long id, @RequestBody User userEntity) {
        return userService.changeUserEmail(id, userEntity);
    }

    @PostMapping("/addSubscriber")
    public Blog addSubscriber(@RequestParam Long userId, Long blogId) {
        return userService.addSubscriber(userId, blogId);
    }

    @GetMapping("/getAuthorBlogs")
    public List<Blog> getAuthorBlogs(@RequestParam Long userId) {
        return userService.getAuthorBlogs(userId);
    }

    @GetMapping("/getSubscriptions")
    public Set<Blog> getSubscriptions(@RequestParam Long userId) {
        return userService.getSubscriptions(userId);
    }

    @DeleteMapping("/unsubscribe")
    public Set<Blog> unsubscribe(@RequestParam Long userId, Long blogId) {
        return userService.unsubscribe(userId, blogId);
    }



    @GetMapping("/security/profile")
    public User getUser(@RequestParam String username) {
        return userService.findByUsername(username);
    }

    // Кастомный вопрос получения всех Authors
    @GetMapping("/getAuthors")
    public List<User> getAuthor() {
        return userService.getAuthor();
    }
}
