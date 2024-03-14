package com.example.BlogPlatform.controllers;

import com.example.BlogPlatform.entities.UserEntity;
import com.example.BlogPlatform.exception.BlogNotFoundExeption;
import com.example.BlogPlatform.exception.UserAlreadyExistExeption;
import com.example.BlogPlatform.exception.UserNotFoundException;
import com.example.BlogPlatform.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController
{
    public UserService userService;

    @GetMapping("/users")
    public ResponseEntity getUsers()
    {
        try
        {
            return ResponseEntity.ok().body(userService.getUsers());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody UserEntity user)
    {
        try
        {
            userService.addUser(user);
            return ResponseEntity.ok().body(user);
        }
        catch (UserAlreadyExistExeption e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @GetMapping("/user")
    public ResponseEntity findUserById(@RequestParam Long id)
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
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam Long id)
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
            return ResponseEntity.badRequest().body("Ошибка " + id);
        }
    }


    @PatchMapping("/user")
    public ResponseEntity changeUserEmail(@RequestParam Long id,
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
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @PatchMapping("/addSubscriber")
    public ResponseEntity addSubscriber(@RequestParam Long userId,
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
            return ResponseEntity.badRequest().body("Ошибка " + userId + blogId);
        }
    }

    @GetMapping("/getAuthorBlogs")
    public ResponseEntity getAuthorBlogs(@RequestParam Long userId)
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
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @GetMapping("/getSubscriptions")
    public ResponseEntity getSubscriptions(@RequestParam Long userId)
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
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }
}