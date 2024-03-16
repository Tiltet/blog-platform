package com.example.blog.repositories;

import com.example.blog.entities.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<BlogEntity, Long>
{
    BlogEntity findByTitle(String title);
}
