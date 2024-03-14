package com.example.BlogPlatform.repositories;

import com.example.BlogPlatform.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsername(String username);
}
