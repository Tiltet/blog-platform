package com.example.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** JavaDoc COMMENT. */
@SuppressWarnings("checkstyle:Indentation")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String avatar;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author",
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Blog> blogs = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "subscribers", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Blog> subscriptions = new HashSet<>();

    @PreRemove
    private void removeUser() {
        blogs.clear();
        subscriptions.forEach(
            subscription -> subscription.getSubscribers().removeAll(Collections.singleton(this)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User otherUser = (User) obj;
        return Objects.equals(this.id, otherUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
