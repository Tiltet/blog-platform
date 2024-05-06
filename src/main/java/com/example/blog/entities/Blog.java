package com.example.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** JavaDoc COMMENT. */
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

    @Column(length = 1000)
    private String description;
    private String img;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> subscribers = new HashSet<>();

    /** JavaDoc COMMENT. */
    @PreRemove
    public void removeAuthor() {
        author.getBlogs().removeAll(Collections.singleton(this));
        subscribers.forEach(
            subscriber -> subscriber.getSubscriptions().removeAll(Collections.singleton(this)));
    }
}
