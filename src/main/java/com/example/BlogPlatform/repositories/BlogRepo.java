package com.example.BlogPlatform.repositories;

import com.example.BlogPlatform.entities.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<BlogEntity, Long>
{
    BlogEntity findByTitle(String title);
}
