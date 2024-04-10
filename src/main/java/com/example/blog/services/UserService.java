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
            throw new IllegalArgumentException(USER_ALREADY_EXIST);
        }
        userRepository.save(userEntity);
        return userEntity;
    }

    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
    }

    public User deleteUser(Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        userRepository.deleteById(id);
        return userEntity;
    }

    public User changeUserEmail(Long id, User user) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        userEntity.setEmail(user.getEmail());
        userRepository.save(userEntity);
        return userEntity;
    }

    public Blog addSubscriber(Long userId, Long blogId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException(BLOG_NOT_FOUND));

        user.getSubscriptions().add(blog);
        blog.getSubscribers().add(user);

        userRepository.save(user);
        blogRepository.save(blog);

        return blog;
    }

    public List<Blog> getAuthorBlogs(Long userId) {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        return userEntity.getBlogs();
    }

    public Set<Blog> getSubscriptions(Long userId) {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        return userEntity.getSubscriptions();
    }

    public Set<Blog> unsubscribe(Long userId, Long blogId) {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException(BLOG_NOT_FOUND));

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