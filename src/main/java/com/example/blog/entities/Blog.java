package com.example.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Collections;
import java.util.Set;
import lombok.*;

@SuppressWarnings("checkstyle:Indentation") // аннотация, игнорирующая отступы
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> subscribers;

    @PreRemove
    public void removeAuthor() {
        author.getBlogs().removeAll(Collections.singleton(this));
        subscribers.forEach(
            subscriber -> subscriber.getSubscriptions().removeAll(Collections.singleton(this)));
    }
}
