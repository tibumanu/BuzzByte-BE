package com.example.BuzzByte.repository;

import com.example.BuzzByte.model.likes.Like;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
@Hidden
public interface LikeRepository<T extends Like> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>
{
    // base class, will be used for PostLike and NewsLike repositories
    public List<T> findAll();
    public List<T> findLikeByUserId(long userId);

}
