package com.example.BlogPlatform.controllers;

import com.example.BlogPlatform.entities.BlogEntity;
import com.example.BlogPlatform.exception.BlogAlreadyExistExeption;
import com.example.BlogPlatform.exception.BlogNotFoundExeption;
import com.example.BlogPlatform.exception.UserNotFoundException;
import com.example.BlogPlatform.services.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
@AllArgsConstructor
public class BlogController
{
    private BlogService blogService;

    @PostMapping("/addBlog")
    public ResponseEntity<Object> addCard(@RequestParam Long authorId,
                                  @RequestBody BlogEntity blog)
    {
        try
        {
            return ResponseEntity.ok().body(blogService.addBlog(authorId, blog));
        }
        catch (BlogAlreadyExistExeption | UserNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @GetMapping("/blogs")
    public ResponseEntity<Object> getBlogs()
    {
        try
        {
            return ResponseEntity.ok().body(blogService.getBlogs());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @DeleteMapping("/deleteBlog")
    public ResponseEntity<Object> deleteBlog(@RequestParam Long blogId)
    {
        try
        {
            return ResponseEntity.ok().body(blogService.deleteBlog(blogId));
        }
        catch (BlogNotFoundExeption e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @PatchMapping("/changeBlogTitle")
    public ResponseEntity<Object> changeBlogTitle(@RequestParam Long blogId,
                                          @RequestBody BlogEntity blog)
    {
        try
        {
            return ResponseEntity.ok().body(blogService.changeBlogTitle(blogId, blog));
        }
        catch (BlogNotFoundExeption | BlogAlreadyExistExeption e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }
}
