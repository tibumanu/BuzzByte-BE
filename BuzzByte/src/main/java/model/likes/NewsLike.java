package model.likes;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import model.news.News;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsLike extends Like {
    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "id", nullable = false)
    private News news;
}
