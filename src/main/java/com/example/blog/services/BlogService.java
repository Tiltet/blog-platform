package com.example.blog.services;


import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.exception.BlogAlreadyExistExeption;
import com.example.blog.exception.BlogNotFoundExeption;
import com.example.blog.exception.UserNotFoundException;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.utilities.CustomCache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@AllArgsConstructor
public class BlogService
{
    private static final String ERROR = "Блог не найден";
    private final CustomCache customCache;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public List<Blog> getBlogs()
    {
        return blogRepository.findAll();
    }

    public Blog addBlog(Long authorId, Blog blog) throws BlogAlreadyExistExeption, UserNotFoundException
    {

        if (blogRepository.findByTitle(blog.getTitle()) != null)
        {
            throw new BlogAlreadyExistExeption("Блог с таким названием уже существует");
        }
        if (userRepository.findById(authorId).isEmpty())
        {
            throw new UserNotFoundException("Такого автора не существует");
        }
        User userEntity = userRepository.findById(authorId).get();

        if (userEntity.getBlogs().isEmpty())
        {
            blog.setAuthor(userEntity);
            customCache.addToCacheUser("authors", userEntity);
        }
        else
        {
            blog.setAuthor(userEntity);
        }

        blogRepository.save(blog);

        return blog;
    }

    public Blog deleteBlog(Long blogId) throws BlogNotFoundExeption
    {
        if (blogRepository.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption(ERROR);
        }
        Blog blog = blogRepository.findById(blogId).get();
        blogRepository.delete(blog);
        return blog;
    }

    public Blog changeBlogTitle(Long blogId, Blog blog) throws BlogNotFoundExeption, BlogAlreadyExistExeption
    {
        if (blogRepository.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption(ERROR);
        }
        if (blogRepository.findByTitle(blog.getTitle()) != null)
        {
            throw new BlogAlreadyExistExeption("Блог с таким названием уже существует");
        }

        Blog blogEntity = blogRepository.findById(blogId).get();
        blogEntity.setTitle(blog.getTitle());
        blogRepository.save(blogEntity);

        return blogEntity;
    }

    public Blog getBlog(Long blogId) throws BlogNotFoundExeption
    {
        if (blogRepository.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption(ERROR);
        }

        return blogRepository.findById(blogId).get();
    }
}