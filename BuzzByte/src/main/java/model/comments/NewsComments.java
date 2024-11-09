package model.comments;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.news.News;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class NewsComments extends Comments {
    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "newsId", nullable = false)
    private News news;
}
