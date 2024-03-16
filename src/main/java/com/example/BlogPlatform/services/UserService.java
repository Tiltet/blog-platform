package com.example.BlogPlatform.services;

import com.example.BlogPlatform.entities.BlogEntity;
import com.example.BlogPlatform.entities.UserEntity;
import com.example.BlogPlatform.exception.BlogNotFoundExeption;
import com.example.BlogPlatform.exception.UserAlreadyExistExeption;
import com.example.BlogPlatform.exception.UserNotFoundException;
import com.example.BlogPlatform.repositories.BlogRepo;
import com.example.BlogPlatform.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserService
{
    private final UserRepo userRepo;
    private final BlogRepo blogRepo;

    public List<UserEntity> getUsers()
    {
        return userRepo.findAll();
    }

    public UserEntity addUser(UserEntity user) throws UserAlreadyExistExeption
    {
        if (userRepo.findByUsername(user.getUsername()) != null)
        {
            throw new UserAlreadyExistExeption("Пользователь с таким username уже существует");
        }
        userRepo.save(user);
        return user;
    }

    public UserEntity findUser(Long id) throws UserNotFoundException
    {
        if (userRepo.findById(id).isEmpty())
        {
            throw new UserNotFoundException("Пользователь " + id + " не найден");
        }
        return userRepo.findById(id).get();
    }

    public UserEntity deleteUser(Long id) throws UserNotFoundException
    {
        UserEntity user = userRepo.findById(id).get();
        if (userRepo.findById(id).isEmpty())
        {
            throw new UserNotFoundException("Пользователь " + id + " не найден");
        }
        userRepo.deleteById(id);
        return user;
    }

    public UserEntity changeUserEmail(Long id, UserEntity user) throws UserNotFoundException
    {
        UserEntity userEntity = userRepo.findById(id).get();
        if (userRepo.findById(id).isEmpty())
        {
            throw new UserNotFoundException("Пользователь " + id + " не найден");
        }
        userEntity.setEmail(user.getEmail());
        userRepo.save(userEntity);
        return userEntity;
    }

    public BlogEntity addSubscriber(Long userId, Long blogId) throws UserNotFoundException, BlogNotFoundExeption
    {
        if (userRepo.findById(userId).isEmpty())
        {
            throw new UserNotFoundException("Пользователь не найден");
        }
        if (blogRepo.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption("Блог не найден");
        }

        UserEntity user = userRepo.findById(userId).get();
        BlogEntity blog = blogRepo.findById(blogId).get();

        user.getSubscriptions().add(blog);
        blog.getSubscribers().add(user);

        userRepo.save(user);
        blogRepo.save(blog);

        return blog;
    }

    public List<BlogEntity> getAuthorBlogs(Long userId) throws UserNotFoundException
    {
        if (userRepo.findById(userId).isEmpty())
        {
            throw new UserNotFoundException("Пользователь не найден");
        }

        UserEntity user = userRepo.findById(userId).get();

        return user.getBlogs();
    }

    public Set<BlogEntity> getSubscriptions(Long userId) throws UserNotFoundException
    {
        if (userRepo.findById(userId).isEmpty())
        {
            throw new UserNotFoundException("Пользователь не найден");
        }

        UserEntity user = userRepo.findById(userId).get();
        return user.getSubscriptions();
    }
}