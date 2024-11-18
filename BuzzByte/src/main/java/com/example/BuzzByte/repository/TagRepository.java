package com.example.BuzzByte.repository;

import com.example.BuzzByte.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findByName(String name);
}
