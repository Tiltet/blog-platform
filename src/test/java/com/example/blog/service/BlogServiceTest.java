package com.example.blog.service;

import com.example.blog.entities.Blog;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.BlogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBlogs_ReturnsAllBlogs() {
        Blog blog1 = new Blog();
        blog1.setTitle("title1");
        blog1.setDescription("description1");

        Blog blog2 = new Blog();
        blog1.setTitle("title2");
        blog1.setDescription("description2");

        List<Blog> blogs = Arrays.asList(blog1, blog2);

        when(blogRepository.findAll()).thenReturn(blogs);

        List<Blog> result = blogService.getBlogs();

        Assertions.assertEquals(blogs, result);
        verify(blogRepository, times(1)).findAll();
    }

    @Test
    public void testGetBlogs_ReturnsEmptyListWhenNoBlogsExist() {
        List<Blog> blogs = Collections.emptyList();

        when(blogRepository.findAll()).thenReturn(blogs);

        List<Blog> result = blogService.getBlogs();

        Assertions.assertEquals(blogs, result);
        verify(blogRepository, times(1)).findAll();
    }
}
