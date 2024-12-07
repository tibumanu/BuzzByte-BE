package com.example.BuzzByte.repository;

import com.example.BuzzByte.model.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface TagRepository extends JpaRepository<Tag, Long> {
    public Optional<Tag> findByName(String name);
    public List<Tag> findAll();
    public Page<Tag> findAll(Specification<Tag> specification, Pageable pageable);
}
