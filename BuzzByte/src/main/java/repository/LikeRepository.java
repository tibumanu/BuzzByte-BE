package repository;

import model.likes.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


@NoRepositoryBean
public interface LikeRepository<T extends Like> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>
{
    // base class, will be used for PostLike and NewsLike repositories
    public List<T> findAll();
    public List<T> findLikeByUserId(long userId);

}
