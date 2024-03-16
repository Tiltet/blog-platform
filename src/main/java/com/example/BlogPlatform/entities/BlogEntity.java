package com.example.BlogPlatform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class BlogEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private UserEntity author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserEntity> subscribers;

    @PreRemove
    public void removeAuthor()
    {
        author.getBlogs().removeAll(Collections.singleton(this));
        subscribers.forEach(subscribers -> subscribers.getSubscriptions().removeAll(Collections.singleton(this)));
    }

    public BlogEntity() {

    }
}