package com.example.blogPlatform.repositories;

import com.example.blogPlatform.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsername(String username);
}
