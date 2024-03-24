package com.example.blog.controllers;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController
{
    private UserService userService;
    private static final Logger logger = Logger.getLogger(ControllerAdvice.class.getName());

    @GetMapping("/users")
    public List<User> getUsers()
    {
        logger.info("Request: /api/v1/users");
        return userService.getUsers();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User userEntity)
    {
        logger.info("Request: api/v1/addUser");
        return userService.addUser(userEntity);
    }

    @GetMapping("/user")
    public User findUserById(@RequestParam Long id)
    {
        logger.info("Request: api/v1/user");
        return userService.findUser(id);
    }

    @DeleteMapping("/deleteUser")
    public User deleteUser(@RequestParam Long id)
    {
        logger.info("Request: api/v1/addUser");
        return userService.deleteUser(id);
    }


    @PatchMapping("/user")
    public User changeUserEmail(@RequestParam Long id,
                                          @RequestBody User userEntity)
    {
        logger.info("Request: api/v1/user");
        return userService.changeUserEmail(id, userEntity);
    }

    @PostMapping("/addSubscriber")
    public Blog addSubscriber(@RequestParam Long userId, Long blogId)
    {
        logger.info("Request: api/v1/addSubscriber");
        return userService.addSubscriber(userId, blogId);
    }

    @GetMapping("/getAuthorBlogs")
    public List<Blog> getAuthorBlogs(@RequestParam Long userId)
    {
        logger.info("Request: api/v1/getAuthorBlogs");
        return userService.getAuthorBlogs(userId);
    }

    @GetMapping("/getSubscriptions")
    public Set<Blog> getSubscriptions(@RequestParam Long userId)
    {
        logger.info("Request: api/v1/getSubscriptions");
        return userService.getSubscriptions(userId);
    }

    @DeleteMapping("/unsubscribe")
    public Set<Blog> unsubscribe(@RequestParam Long userId, Long blogId)
    {
        logger.severe("Request: api/v1/unsubscribe");
        return userService.unsubscribe(userId, blogId);
    }

    // Кастомный вопрос получения всех Authors
    @GetMapping("/getAuthors")
    public List<User> getAuthor()
    {
        logger.info("Request: api/v1/getAuthors");
        return userService.getAuthor();
    }
}