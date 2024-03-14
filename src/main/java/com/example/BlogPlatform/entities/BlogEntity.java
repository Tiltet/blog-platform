package com.example.BlogPlatform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class BlogEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JsonIgnore
    private UserEntity author;

    @ManyToMany
    @JsonIgnore
    private Set<UserEntity> subscribers;
}