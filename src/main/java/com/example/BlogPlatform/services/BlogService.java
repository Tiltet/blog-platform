package com.example.BlogPlatform.services;


import com.example.BlogPlatform.entities.BlogEntity;
import com.example.BlogPlatform.entities.UserEntity;
import com.example.BlogPlatform.exception.BlogAlreadyExistExeption;
import com.example.BlogPlatform.exception.BlogNotFoundExeption;
import com.example.BlogPlatform.exception.UserNotFoundException;
import com.example.BlogPlatform.repositories.BlogRepo;
import com.example.BlogPlatform.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlogService
{
    private final BlogRepo blogRepo;
    private final UserRepo userRepo;

    public List<BlogEntity> getBlogs()
    {
        return blogRepo.findAll();
    }

    public BlogEntity addBlog(Long authorId, BlogEntity blog) throws BlogAlreadyExistExeption, UserNotFoundException
    {

        if (blogRepo.findByTitle(blog.getTitle()) != null)
        {
            throw new BlogAlreadyExistExeption("Блог с таким названием уже существует");
        }
        if (userRepo.findById(authorId).isEmpty())
        {
            throw new UserNotFoundException("Такого автора не существует");
        }
        UserEntity user = userRepo.findById(authorId).get();

        blog.setAuthor(user);
        blogRepo.save(blog);

        return blog;
    }

    public BlogEntity deleteBlog(Long blogId) throws BlogNotFoundExeption
    {
        if (blogRepo.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption("Блог не найден");
        }
        BlogEntity blog = blogRepo.findById(blogId).get();
        blogRepo.delete(blog);
        return blog;
    }

    public BlogEntity changeBlogTitle(Long blogId, BlogEntity blog) throws BlogNotFoundExeption, BlogAlreadyExistExeption
    {
        if (blogRepo.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption("Блог не найден");
        }
        if (blogRepo.findByTitle(blog.getTitle()) != null)
        {
            throw new BlogAlreadyExistExeption("Блог с таким названием уже существует");
        }

        BlogEntity blogEntity = blogRepo.findById(blogId).get();
        blogEntity.setTitle(blog.getTitle());
        blogRepo.save(blogEntity);

        return blogEntity;
    }
}