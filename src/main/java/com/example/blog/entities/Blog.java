package com.example.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.Collections;
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
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> subscribers;

    /** JavaDoc COMMENT. */
    @PreRemove
    public void removeAuthor() {
        author.getBlogs().removeAll(Collections.singleton(this));
        subscribers.forEach(
            subscriber -> subscriber.getSubscriptions().removeAll(Collections.singleton(this)));
    }
}
