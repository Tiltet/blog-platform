package com.example.blog.services;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * JavaDoc COMMENT.
 */
@SuppressWarnings("checkstyle:Indentation")
@Service
@Getter
@Setter
@AllArgsConstructor
public class BlogService {
    private static final String USER_NOT_FOUND = "User not found";
    private static final String BLOG_NOT_FOUND = "Blog not found";
    private static final String USER_ALREADY_EXIST = "User already exist";
    private static final String BLOG_ALREADY_EXIST = "Blog already exist";

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    /** JavaDoc COMMENT. */
    public Blog addBlog(Long authorId, Blog blog) {
        if (userRepository.findById(authorId).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }

        User userEntity = userRepository.findById(authorId).get();

        blog.setAuthor(userEntity);
        blogRepository.save(blog);

        return blog;
    }

    /** JavaDoc COMMENT. */
    public Blog deleteBlog(Long blogId) {
        if (blogRepository.findById(blogId).isEmpty()) {
            throw new IllegalArgumentException(BLOG_NOT_FOUND);
        }

        Blog blog = blogRepository.findById(blogId).get();
        blogRepository.delete(blog);
        return blog;
    }

    /** JavaDoc COMMENT. */
    public Blog changeBlogTitle(Long blogId, Blog blog) {
        if (blogRepository.findById(blogId).isEmpty()) {
            throw new IllegalArgumentException(BLOG_NOT_FOUND);
        }

        Blog blogEntity = blogRepository.findById(blogId).get();
        blogEntity.setTitle(blog.getTitle());
        blogRepository.save(blogEntity);

        return blogEntity;
    }

    public Set<User> getBlogsAuthors(List<Blog> blogs) {
        Set<User> authors = new HashSet<>();
        for (Blog blog : blogs) {
            Blog blogEntity = blogRepository.findById(blog.getId()).get();
            User author = blogEntity.getAuthor();
            authors.add(author);
        }
        return authors;
    }


    /** JavaDoc COMMENT. */
    public Blog getBlog(Long blogId) {
        if (blogRepository.findById(blogId).isEmpty()) {
            throw new IllegalArgumentException(BLOG_NOT_FOUND);
        }
        return blogRepository.findById(blogId).get();
    }
}