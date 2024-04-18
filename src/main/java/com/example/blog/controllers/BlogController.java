package com.example.blog.controllers;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.services.BlogService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/** JavaDoc COMMENT. */
@SuppressWarnings("checkstyle:Indentation")
@RestController
@RequestMapping("/api/v2")
@AllArgsConstructor
@CrossOrigin("*")
public class BlogController {

    private BlogService blogService;

    @PostMapping("/addBlog")
    public Blog addBlog(@RequestParam Long authorId, @RequestBody Blog blog) {
        return blogService.addBlog(authorId, blog);
    }

    @GetMapping("/blogs")
    public List<Blog> getBlogs() {
        return blogService.getBlogs();
    }

    @DeleteMapping("/deleteBlog")
    public Blog deleteBlog(@RequestParam Long blogId) {
        return blogService.deleteBlog(blogId);
    }

    @PatchMapping("/changeBlogTitle")
    public Blog changeBlogTitle(@RequestParam Long blogId, @RequestBody Blog blog) {
        return blogService.changeBlogTitle(blogId, blog);
    }

    @GetMapping("/blog")
    public Blog getBlog(@RequestParam Long blogId) {
        return blogService.getBlog(blogId);
    }

    /** Bulk функция. */
    @PostMapping("/bulk")
    public Set<User> getBlogsAuthors(@RequestBody List<Blog> blogs) {
        return blogService.getBlogsAuthors(blogs);
    }
}
