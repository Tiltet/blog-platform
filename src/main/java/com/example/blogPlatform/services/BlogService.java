package com.example.blogPlatform.services;


import com.example.blogPlatform.entities.BlogEntity;
import com.example.blogPlatform.entities.UserEntity;
import com.example.blogPlatform.exception.BlogAlreadyExistExeption;
import com.example.blogPlatform.exception.BlogNotFoundExeption;
import com.example.blogPlatform.exception.UserNotFoundException;
import com.example.blogPlatform.repositories.BlogRepo;
import com.example.blogPlatform.repositories.UserRepo;
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