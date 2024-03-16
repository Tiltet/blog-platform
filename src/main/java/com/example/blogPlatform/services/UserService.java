package com.example.blogPlatform.services;

import com.example.blogPlatform.entities.BlogEntity;
import com.example.blogPlatform.entities.UserEntity;
import com.example.blogPlatform.exception.BlogNotFoundExeption;
import com.example.blogPlatform.exception.UserAlreadyExistExeption;
import com.example.blogPlatform.exception.UserNotFoundException;
import com.example.blogPlatform.repositories.BlogRepo;
import com.example.blogPlatform.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Getter
@Setter
@AllArgsConstructor
@Transactional
public class UserService
{
    private final UserRepo userRepo;
    private final BlogRepo blogRepo;
    private static final String ERROR = "Пользователь не найден";

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
            throw new UserNotFoundException(ERROR);
        }
        return userRepo.findById(id).get();
    }

    public UserEntity deleteUser(Long id) throws UserNotFoundException
    {
        UserEntity user = userRepo.findById(id).get();
        if (userRepo.findById(id).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }
        userRepo.deleteById(id);
        return user;
    }

    public UserEntity changeUserEmail(Long id, UserEntity user) throws UserNotFoundException
    {
        UserEntity userEntity = userRepo.findById(id).get();
        if (userRepo.findById(id).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }
        userEntity.setEmail(user.getEmail());
        userRepo.save(userEntity);
        return userEntity;
    }

    public BlogEntity addSubscriber(Long userId, Long blogId) throws UserNotFoundException, BlogNotFoundExeption
    {
        if (userRepo.findById(userId).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
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
            throw new UserNotFoundException(ERROR);
        }

        UserEntity user = userRepo.findById(userId).get();

        return user.getBlogs();
    }

    public Set<BlogEntity> getSubscriptions(Long userId) throws UserNotFoundException
    {
        if (userRepo.findById(userId).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }

        UserEntity user = userRepo.findById(userId).get();
        return user.getSubscriptions();
    }
}