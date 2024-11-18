package com.example.BuzzByte.repository;

import com.example.BuzzByte.model.comments.NewsComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsCommentRepository extends CommentRepository<NewsComment> {
    public List<NewsComment> findAll();
    public NewsComment findById(long id);
    // method to return all comments for a news
    public List<NewsComment> findNewsCommentByNewsId(long newsId);
    // method to return all comments for a user
    public List<NewsComment> findNewsCommentByUserId(long userId);
}
