package com.example.BuzzByte.repository;


import com.example.BuzzByte.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    public List<Post> findAll();

    public Post findById(long id);
    public Optional<Post> findByTitle(String title);
    public List<Post> findByUserId(long userId);
}
