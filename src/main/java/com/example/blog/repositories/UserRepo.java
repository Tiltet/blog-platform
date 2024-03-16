package com.example.blog.repositories;

import com.example.blog.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsername(String username);
}
