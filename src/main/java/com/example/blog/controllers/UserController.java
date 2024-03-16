package com.example.blog.controllers;

import com.example.blog.entities.UserEntity;
import com.example.blog.exception.BlogNotFoundExeption;
import com.example.blog.exception.UserAlreadyExistExeption;
import com.example.blog.exception.UserNotFoundException;
import com.example.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController
{
    private static final String ERROR = "Ошибка";
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers()
    {
        try
        {
            return ResponseEntity.ok().body(userService.getUsers());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody UserEntity user)
    {
        try
        {
            return ResponseEntity.ok().body(userService.addUser(user));
        }
        catch (UserAlreadyExistExeption e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<Object> findUserById(@RequestParam Long id)
    {
        try
        {
            return ResponseEntity.ok().body(userService.findUser(id));
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam Long id)
    {
        try
        {
            return ResponseEntity.ok().body(userService.deleteUser(id));
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR + id);
        }
    }


    @PatchMapping("/user")
    public ResponseEntity<Object> changeUserEmail(@RequestParam Long id,
                                          @RequestBody UserEntity user)
    {
        try
        {
            return ResponseEntity.ok().body(userService.changeUserEmail(id, user));
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR);
        }
    }

    @PostMapping("/addSubscriber")
    public ResponseEntity<Object> addSubscriber(@RequestParam Long userId,
                                        Long blogId)
    {
        try
        {
            return ResponseEntity.ok().body(userService.addSubscriber(userId, blogId));
        }
        catch (UserNotFoundException | BlogNotFoundExeption e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR + userId + blogId);
        }
    }

    @GetMapping("/getAuthorBlogs")
    public ResponseEntity<Object> getAuthorBlogs(@RequestParam Long userId)
    {
        try
        {
            return ResponseEntity.ok().body(userService.getAuthorBlogs(userId));
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR);
        }
    }

    @GetMapping("/getSubscriptions")
    public ResponseEntity<Object> getSubscriptions(@RequestParam Long userId)
    {
        try
        {
            return ResponseEntity.ok().body(userService.getSubscriptions(userId));
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR);
        }
    }
}