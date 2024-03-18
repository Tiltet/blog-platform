package com.example.blog.services;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.exception.BlogNotFoundExeption;
import com.example.blog.exception.UserAlreadyExistExeption;
import com.example.blog.exception.UserNotFoundException;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private static final String ERROR = "Пользователь не найден";

    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public User addUser(User userEntity) throws UserAlreadyExistExeption
    {
        if (userRepository.findByUsername(userEntity.getUsername()) != null)
        {
            throw new UserAlreadyExistExeption("Пользователь с таким username уже существует");
        }
        userRepository.save(userEntity);
        return userEntity;
    }

    public User findUser(Long id) throws UserNotFoundException
    {
        if (userRepository.findById(id).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }
        return userRepository.findById(id).get();
    }

    public User deleteUser(Long id) throws UserNotFoundException
    {
        User userEntity = userRepository.findById(id).get();
        if (userRepository.findById(id).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }
        userRepository.deleteById(id);
        return userEntity;
    }

    public User changeUserEmail(Long id, User user) throws UserNotFoundException
    {
        User userEntity = userRepository.findById(id).get();
        if (userRepository.findById(id).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }
        userEntity.setEmail(user.getEmail());
        userRepository.save(userEntity);
        return userEntity;
    }

    public Blog addSubscriber(Long userId, Long blogId) throws UserNotFoundException, BlogNotFoundExeption
    {
        if (userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }
        if (blogRepository.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption("Блог не найден");
        }

        User userEntity = userRepository.findById(userId).get();
        Blog blog = blogRepository.findById(blogId).get();

        userEntity.getSubscriptions().add(blog);
        blog.getSubscribers().add(userEntity);

        userRepository.save(userEntity);
        blogRepository.save(blog);

        return blog;
    }

    public List<Blog> getAuthorBlogs(Long userId) throws UserNotFoundException
    {
        if (userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }

        User userEntity = userRepository.findById(userId).get();

        return userEntity.getBlogs();
    }

    public Set<Blog> getSubscriptions(Long userId) throws UserNotFoundException
    {
        if (userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }

        User userEntity = userRepository.findById(userId).get();
        return userEntity.getSubscriptions();
    }

    public Set<Blog> unsubscribe(Long userId, Long blogId) throws UserNotFoundException, BlogNotFoundExeption
    {
        if (userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException(ERROR);
        }
        if (blogRepository.findById(blogId).isEmpty())
        {
            throw new BlogNotFoundExeption("Блог не найден");
        }

        User userEntity = userRepository.findById(userId).get();
        Blog blog = blogRepository.findById(blogId).get();

        Set<Blog> setBlogEntities = userEntity.getSubscriptions();
        Set<User> setUserEntities = blog.getSubscribers();

        setUserEntities.remove(userEntity);
        setBlogEntities.remove(blog);

        userEntity.setSubscriptions(setBlogEntities);
        blog.setSubscribers(setUserEntities);

        userRepository.save(userEntity);
        blogRepository.save(blog);

        return setBlogEntities;
    }
}