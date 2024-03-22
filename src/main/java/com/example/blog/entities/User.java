package com.example.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Blog> blogs = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "subscribers", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Blog> subscriptions = new HashSet<>();

    @PreRemove
    private void removeUser()
    {
        blogs.clear();
        subscriptions.forEach(subscription -> subscription.getSubscribers().removeAll(Collections.singleton(this)));
    }
}
