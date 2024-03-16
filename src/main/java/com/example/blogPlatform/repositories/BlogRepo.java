package com.example.blogPlatform.repositories;

import com.example.blogPlatform.entities.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<BlogEntity, Long>
{
    BlogEntity findByTitle(String title);
}
