package com.example.blog.repositories;

import com.example.blog.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long>
{
    Blog findByTitle(String title);
}
