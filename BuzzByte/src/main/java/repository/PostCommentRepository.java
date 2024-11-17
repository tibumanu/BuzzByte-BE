package repository;

import model.comments.PostComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends CommentRepository<PostComment> {
    public List<PostComment> findAll();
    public PostComment findById(long id);
    // method to return all comments for a post
    public List<PostComment> findPostCommentByPostId(long postId);
    // method to return all comments for a user
    public List<PostComment> findPostCommentByUserId(long userId);
}
