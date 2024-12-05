package com.example.BuzzByte.model;

import jakarta.persistence.*;
import lombok.*;
import com.example.BuzzByte.model.comments.PostComment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 5000)
    private String content;
    @ManyToMany
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"
            )
    )
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    private String image;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments;
    private Long likes = 0L;
    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false) // prevent updates to this field after creation
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = true, name = "updated_at")
    private LocalDateTime updatedAt;
}
