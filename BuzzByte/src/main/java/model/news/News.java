package model.news;

import jakarta.persistence.*;
import lombok.*;
import model.Tag;
import model.comments.NewsComment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    @ManyToMany
    @JoinTable(
            name = "news_tags",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsComment> comments;
}
