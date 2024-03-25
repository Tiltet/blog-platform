package com.example.blog.repositories;

import com.example.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.blogs")
    Set<User> findAllAuthors();
}
