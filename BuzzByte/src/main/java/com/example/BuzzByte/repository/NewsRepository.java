package com.example.BuzzByte.repository;

import com.example.BuzzByte.model.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    public List<News> findAll();
    public News findById(long id);
    public Optional<News> findByTitle(String title);


}
