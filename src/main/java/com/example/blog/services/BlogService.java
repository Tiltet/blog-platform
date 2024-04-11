package com.example.blog.services;

import com.example.blog.entities.Blog;
import com.example.blog.entities.User;
import com.example.blog.repositories.BlogRepository;
import com.example.blog.repositories.UserRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

    /** Добавление блога. */
    public Blog addBlog(Long authorId, Blog blog) {
        if (blogRepository.findByTitle(blog.getTitle()) != null) {
            throw new IllegalArgumentException(BLOG_ALREADY_EXIST);
        }

        User user = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        blog.setAuthor(user);
        blogRepository.save(blog);

        return blog;
    }

    /** Удаление блога. */
    public Blog deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException(BLOG_NOT_FOUND));

        blogRepository.delete(blog);
        return blog;
    }

    /** Получить блог. */
    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException(BLOG_NOT_FOUND));
    }

    /** Изменение заголовка блога. */
    public Blog changeBlogTitle(Long blogId, Blog blog) {
        Blog blogEntity = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException(BLOG_NOT_FOUND));

        blogEntity.setTitle(blog.getTitle());
        blogRepository.save(blogEntity);

        return blogEntity;
    }

    /** Получить всех авторов на платформе. */
    public Set<User> getBlogsAuthors(List<Blog> blogs) {
        return blogs.stream()
                .map(blog -> blogRepository.findById(blog.getId())
                        .orElseThrow(() -> new IllegalArgumentException(BLOG_NOT_FOUND)))
                .map(Blog::getAuthor).collect(Collectors.toSet());
    }
}