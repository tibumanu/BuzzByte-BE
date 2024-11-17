package repository;


import model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAll();
    public Post findById(long id);
    public Optional<Post> findByTitle(String title);
    public List<Post> findByUserId(long userId);
}
