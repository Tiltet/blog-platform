package com.example.blog.controllers;

import com.example.blog.entities.Blog;
import com.example.blog.services.BlogService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("checkstyle:Indentation")
@RestController
@RequestMapping("/api/v2")
@AllArgsConstructor
public class BlogController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private BlogService blogService;

    @PostMapping("/addBlog")
    public Blog addCard(@RequestParam Long authorId, @RequestBody Blog blog) {
        logger.info("Request: api/v2/addBlog");
        return blogService.addBlog(authorId, blog);
    }

    @GetMapping("/blogs")
    public List<Blog> getBlogs() {
        logger.info("Request: api/v2/blogs");
        return blogService.getBlogs();
    }

    @DeleteMapping("/deleteBlog")
    public Blog deleteBlog(@RequestParam Long blogId) {
        logger.info("Request: api/v2/deleteBlog");
        return blogService.deleteBlog(blogId);
    }

    @PatchMapping("/changeBlogTitle")
    public Blog changeBlogTitle(@RequestParam Long blogId, @RequestBody Blog blog) {
        logger.info("Request: api/v2/changeBlogTitle");
        return blogService.changeBlogTitle(blogId, blog);
    }

    @GetMapping("/blog")
    public Blog getBlog(@RequestParam Long blogId) {
        logger.info("Request: api/v2/blog");
        return blogService.getBlog(blogId);
    }
}
