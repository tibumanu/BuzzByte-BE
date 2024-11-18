package repository;

import model.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@NoRepositoryBean
public interface CommentRepository<T extends Comment> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>{
    // base repository, will be used for PostComment and NewsComment repositories
    public List<T> findAll();
    public List<T> findCommentByUserId(long userId);
}
