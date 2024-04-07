package com.example.blog.services;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/** JavaDoc COMMENT. */
@SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
@Service
@Getter
@Setter
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private static final String USER_NOT_FOUND = "User not found";
    private static final String BLOG_NOT_FOUND = "Blog not found";
    private static final String USER_ALREADY_EXIST = "User already exist";
    private static final String BLOG_ALREADY_EXIST = "Blog already exist";


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User userEntity) {
        if (userRepository.findByUsername(userEntity.getUsername()) != null) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        userRepository.save(userEntity);
        return userEntity;
    }

    public User findUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        return userRepository.findById(id).get();
    }

    public User deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        User userEntity = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return userEntity;
    }

    public User changeUserEmail(Long id, User user) {
        if (userRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        User userEntity = userRepository.findById(id).get();
        userEntity.setEmail(user.getEmail());
        userRepository.save(userEntity);
        return userEntity;
    }

    public Blog addSubscriber(Long userId, Long blogId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        if (blogRepository.findById(blogId).isEmpty()) {
            throw new IllegalArgumentException(BLOG_NOT_FOUND);
        }

        User userEntity = userRepository.findById(userId).get();
        Blog blog = blogRepository.findById(blogId).get();

        userEntity.getSubscriptions().add(blog);
        blog.getSubscribers().add(userEntity);

        userRepository.save(userEntity);
        blogRepository.save(blog);

        return blog;
    }

    public List<Blog> getAuthorBlogs(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        User userEntity = userRepository.findById(userId).get();

        return userEntity.getBlogs();
    }

    public Set<Blog> getSubscriptions(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        User userEntity = userRepository.findById(userId).get();
        return userEntity.getSubscriptions();
    }

    public Set<Blog> unsubscribe(Long userId, Long blogId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        if (blogRepository.findById(blogId).isEmpty()) {
            throw new IllegalArgumentException(BLOG_NOT_FOUND);
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

    public List<User> getAuthor() {
        return userRepository.findAllAuthors();
    }
}