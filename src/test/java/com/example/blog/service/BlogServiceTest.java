package com.example.blog.service;

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

import static org.mockito.Mockito.*;

class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBlog_WhenBlogDoesNotExist_ShouldReturnBlog() {
        Blog blog = new Blog();
        blog.setTitle("My Blog");

        when(blogRepository.findByTitle(blog.getTitle())).thenReturn(null);

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Blog createdBlog = blogService.addBlog(1L, blog);

        Assertions.assertNotNull(createdBlog);
        Assertions.assertEquals(blog.getTitle(), createdBlog.getTitle());
        Assertions.assertEquals(user, createdBlog.getAuthor());

        verify(blogRepository, times(1)).findByTitle(blog.getTitle());
        verify(userRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void testAddBlog_WhenBlogExists_ShouldThrowException() {
        Blog blog = new Blog();
        blog.setTitle("My Blog");

        when(blogRepository.findByTitle(blog.getTitle())).thenReturn(blog);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            blogService.addBlog(1L, blog);
        });

        verify(blogRepository, times(1)).findByTitle(blog.getTitle());
        verify(userRepository, never()).findById(anyLong());
        verify(blogRepository, never()).save(any(Blog.class));
    }

    @Test
    void testAddBlog_WhenAuthorNotFound_ShouldThrowException() {
        Blog blog = new Blog();
        blog.setTitle("My Blog");

        when(blogRepository.findByTitle(blog.getTitle())).thenReturn(null);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            blogService.addBlog(1L, blog);
        });

        verify(blogRepository, times(1)).findByTitle(blog.getTitle());
        verify(userRepository, times(1)).findById(1L);
        verify(blogRepository, never()).save(any(Blog.class));
    }

    @Test
    void testGetBlogs_WhenBlogsExist_ShouldReturnBlogs() {
        Blog blog1 = new Blog();
        blog1.setId(1L);
        blog1.setTitle("blog1");

        Blog blog2 = new Blog();
        blog1.setId(2L);
        blog1.setTitle("blog2");

        Blog blog3 = new Blog();
        blog1.setId(3L);
        blog1.setTitle("blog3");

        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog1);
        blogs.add(blog2);
        blogs.add(blog3);

        when(blogRepository.findAll()).thenReturn(blogs);

        List<Blog> result = blogService.getBlogs();

        Assertions.assertEquals(blogs.size(), result.size());
        Assertions.assertEquals(blogs.get(0), result.get(0));
        Assertions.assertEquals(blogs.get(1), result.get(1));
        Assertions.assertEquals(blogs.get(2), result.get(2));

        verify(blogRepository, times(1)).findAll();
    }

    @Test
    void testGetBlogs_WhenNoBlogsExist_ShouldReturnEmptyList() {
        List<Blog> emptyList = new ArrayList<>();

        when(blogRepository.findAll()).thenReturn(emptyList);

        List<Blog> result = blogService.getBlogs();

        Assertions.assertTrue(result.isEmpty());

        verify(blogRepository, times(1)).findAll();
    }

    @Test
    void testDeleteBlog_WhenBlogExists_ShouldReturnDeletedBlog() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("blog");

        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));

        Blog deletedBlog = blogService.deleteBlog(1L);

        Assertions.assertEquals(blog, deletedBlog);

        verify(blogRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).delete(blog);
    }

    @Test
    void testDeleteBlog_WhenBlogDoesNotExist_ShouldThrowException() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            blogService.deleteBlog(1L);
        });

        verify(blogRepository, times(1)).findById(1L);
        verify(blogRepository, never()).delete(any(Blog.class));
    }

    @Test
    void testGetBlog_WhenBlogExists_ShouldReturnBlog() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("Test Blog");

        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));

        Blog result = blogService.getBlog(1L);

        Assertions.assertEquals(blog, result);

        verify(blogRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBlog_WhenBlogDoesNotExist_ShouldThrowException() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            blogService.getBlog(1L);
        });

        verify(blogRepository, times(1)).findById(1L);
    }

    @Test
    void testChangeBlogTitle_WhenBlogExists_ShouldChangeTitle() {
        String newTitle = "New Title";
        Blog existingBlog = new Blog();
        existingBlog.setId(1L);
        existingBlog.setTitle("Old Title");

        when(blogRepository.findById(1L)).thenReturn(Optional.of(existingBlog));

        Blog updatedBlog = new Blog();
        updatedBlog.setTitle(newTitle);
        Blog result = blogService.changeBlogTitle(1L, updatedBlog);

        Assertions.assertEquals(newTitle, result.getTitle());

        verify(blogRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).save(existingBlog);
    }

    @Test
    void testChangeBlogTitle_WhenBlogDoesNotExist_ShouldThrowException() {
        String newTitle = "New Title";

        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        Blog updatedBlog = new Blog();
        updatedBlog.setTitle(newTitle);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            blogService.changeBlogTitle(1L, updatedBlog);
        });

        verify(blogRepository, times(1)).findById(1L);
        verify(blogRepository, never()).save(any(Blog.class));
    }

    @Test
    void testGetBlogsAuthors_WhenBlogsExist_ShouldReturnAuthors() {
        Blog blog1 = new Blog();
        blog1.setId(1L);
        blog1.setTitle("Blog 1");
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("Author 1");
        blog1.setAuthor(user1);

        Blog blog2 = new Blog();
        blog2.setId(2L);
        blog2.setTitle("Blog 2");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("Author 2");
        blog2.setAuthor(user2);

        List<Blog> blogs = Arrays.asList(blog1, blog2);

        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog1));
        when(blogRepository.findById(2L)).thenReturn(Optional.of(blog2));

        Set<User> authors = blogService.getBlogsAuthors(blogs);

        Assertions.assertEquals(2, authors.size());
        Assertions.assertTrue(authors.contains(user1));
        Assertions.assertTrue(authors.contains(user2));

        verify(blogRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).findById(2L);
    }

    @Test
    void testGetBlogsAuthors_WhenBlogNotFound_ShouldThrowException() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("Blog 1");

        List<Blog> blogs = List.of(blog);

        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            blogService.getBlogsAuthors(blogs);
        });

        verify(blogRepository, times(1)).findById(1L);
    }
}
