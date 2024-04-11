package com.example.blog.controllers;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.BlogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlogControllerTest {
    @InjectMocks
    private BlogController blogController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private BlogService blogService = new BlogService(blogRepository, userRepository);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCard_WithExistingAuthor_ShouldReturnNewBlog() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        userRepository.save(user);

        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("New Blog");

        when(blogService.addBlog(1L, blog)).thenReturn(blog);

        Blog response = blogController.addBlog(1L, blog);

        assertEquals(blog, response);

        verify(blogService, times(1)).addBlog(1L, blog);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testAddCard_WithNonExistingAuthor_ShouldThrowException() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("New Blog");

        when(blogService.addBlog(1L, blog)).thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Blog response = blogController.addBlog(1L, blog);
        });

        verify(blogService, times(1)).addBlog(1L, blog);
    }

    @Test
    public void testGetBlogs_ReturnsEmptyList() {
        when(blogService.getBlogs()).thenReturn(Collections.emptyList());

        List<Blog> actualBlogs = blogController.getBlogs();

        assertTrue(actualBlogs.isEmpty());

        verify(blogService, times(1)).getBlogs();
    }

    @Test
    public void testGetBlogs_ReturnsBlogs() {
        List<Blog> expectedBlogs = new ArrayList<>();

        Blog blog1 = new Blog();
        blog1.setId(1L);
        blog1.setTitle("blog1");

        Blog blog2 = new Blog();
        blog2.setId(2L);
        blog2.setTitle("blog2");

        expectedBlogs.add(blog1);
        expectedBlogs.add(blog2);

        when(blogService.getBlogs()).thenReturn(expectedBlogs);

        List<Blog> actualBlogs = blogController.getBlogs();

        assertEquals(expectedBlogs, actualBlogs);

        verify(blogService, times(1)).getBlogs();
    }

    @Test
    public void testDeleteBlog_ExistingBlog_ReturnsDeletedBlog() {
        Blog deletedBlog = new Blog();
        deletedBlog.setId(1L);
        deletedBlog.setTitle("blog1");

        when(blogService.deleteBlog(1L)).thenReturn(deletedBlog);

        Blog actualDeletedBlog = blogController.deleteBlog(1L);

        assertEquals(deletedBlog, actualDeletedBlog);

        verify(blogService, times(1)).deleteBlog(1L);
    }

    @Test
    public void testDeleteBlog_NonExistingBlog_ReturnsNull() {
        when(blogService.deleteBlog(1L)).thenReturn(null);

        Blog actualDeletedBlog = blogController.deleteBlog(1L);

        assertNull(actualDeletedBlog);

        verify(blogService, times(1)).deleteBlog(1L);
    }

    @Test
    public void testChangeBlogTitle_ExistingBlog_ReturnsUpdatedBlog() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("blog1");

        when(blogService.changeBlogTitle(1L, blog)).thenReturn(blog);

        Blog actualUpdatedBlog = blogController.changeBlogTitle(1L, blog);

        assertEquals(blog, actualUpdatedBlog);

        verify(blogService, times(1)).changeBlogTitle(1L, blog);
    }

    @Test
    public void testChangeBlogTitle_NonExistingBlog_ReturnsNull() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("blog1");

        when(blogService.changeBlogTitle(1L, blog)).thenReturn(null);

        Blog actualUpdatedBlog = blogController.changeBlogTitle(1L, blog);

        assertNull(actualUpdatedBlog);

        verify(blogService, times(1)).changeBlogTitle(1L, blog);
    }

    @Test
    public void testGetBlog_ExistingBlog_ReturnsBlog() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("blog1");

        when(blogService.getBlog(1L)).thenReturn(blog);

        Blog actualBlog = blogController.getBlog(1L);

        assertEquals(blog, actualBlog);

        verify(blogService, times(1)).getBlog(1L);
    }

    @Test
    public void testGetBlog_NonExistingBlog_ReturnsNull() {
        when(blogService.getBlog(1L)).thenReturn(null);

        Blog actualBlog = blogController.getBlog(1L);

        assertNull(actualBlog);

        verify(blogService, times(1)).getBlog(1L);
    }

    @Test
    public void testGetBlogsAuthors_ReturnsAuthorsSet() {
        Blog blog1 = new Blog();
        blog1.setId(1L);
        blog1.setTitle("blog1");

        Blog blog2 = new Blog();
        blog2.setId(2L);
        blog2.setTitle("blog2");

        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog1);
        blogs.add(blog2);

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        Set<User> authors = new HashSet<>();
        authors.add(user1);
        authors.add(user2);

        when(blogService.getBlogsAuthors(blogs)).thenReturn(authors);

        Set<User> actualAuthors = blogController.getBlogsAuthors(blogs);

        assertEquals(authors, actualAuthors);

        verify(blogService, times(1)).getBlogsAuthors(blogs);
    }

    @Test
    public void testGetBlogsAuthors_EmptyBlogsList_ReturnsEmptySet() {
        List<Blog> blogs = new ArrayList<>();

        Set<User> expectedAuthors = new HashSet<>();

        when(blogService.getBlogsAuthors(blogs)).thenReturn(expectedAuthors);

        Set<User> actualAuthors = blogController.getBlogsAuthors(blogs);

        assertEquals(expectedAuthors, actualAuthors);
        
        verify(blogService, times(1)).getBlogsAuthors(blogs);
    }
}
