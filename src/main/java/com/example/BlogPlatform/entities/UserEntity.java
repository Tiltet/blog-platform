package com.example.BlogPlatform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<BlogEntity> blogs = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "subscribers", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<BlogEntity> subscriptions = new HashSet<>();

    public UserEntity() {

    }

    @PreRemove
    private void removeUser()
    {
        blogs.clear();
        subscriptions.forEach(subscriptions -> subscriptions.getSubscribers().removeAll(Collections.singleton(this)));
    }
}
