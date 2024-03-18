package com.example.blog.controllers;

import com.example.blog.entities.Blog;
import com.example.blog.exception.BlogAlreadyExistExeption;
import com.example.blog.exception.BlogNotFoundExeption;
import com.example.blog.exception.UserNotFoundException;
import com.example.blog.services.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
@AllArgsConstructor
public class BlogController
{
    private static final String ERROR = "Ошибка";
    private BlogService blogService;

    @PostMapping("/addBlog")
    public ResponseEntity<Object> addCard(@RequestParam Long authorId,
                                  @RequestBody Blog blog)
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
            return ResponseEntity.badRequest().body(ERROR);
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
            return ResponseEntity.badRequest().body(ERROR);
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
            return ResponseEntity.badRequest().body(ERROR);
        }
    }

    @PatchMapping("/changeBlogTitle")
    public ResponseEntity<Object> changeBlogTitle(@RequestParam Long blogId,
                                          @RequestBody Blog blog)
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
            return ResponseEntity.badRequest().body(ERROR);
        }
    }

    @GetMapping("/blog")
    public ResponseEntity<Object> getBlog(@RequestParam Long blogId)
    {
        try
        {
            return ResponseEntity.ok().body(blogService.getBlog(blogId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ERROR);
        }
    }
}
