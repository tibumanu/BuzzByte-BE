package com.example.BuzzByte.repository;

import com.example.BuzzByte.model.likes.NewsLike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsLikeRepository extends LikeRepository<NewsLike> {
    public List<NewsLike> findAll();
    public NewsLike findById(long id);
    // method to return all likes for a news
    public List<NewsLike> findNewsLikeByNewsId(long newsId);
    // method to return all likes for a user
    public List<NewsLike> findNewsLikeByUserId(long userId);
}
