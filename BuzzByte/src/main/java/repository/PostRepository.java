package repository;


import model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findAll(Pageable pageable);
    public Post findById(long id);
    public Optional<Post> findByTitle(String title);
    public List<Post> findByUserId(long userId);
}
