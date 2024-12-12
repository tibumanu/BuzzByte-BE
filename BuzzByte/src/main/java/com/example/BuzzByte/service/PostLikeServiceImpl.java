package com.example.BuzzByte.service;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.model.likes.PostLike;
import com.example.BuzzByte.repository.PostLikeRepository;
import com.example.BuzzByte.repository.PostRepository;
import com.example.BuzzByte.repository.UserRepository;
import com.example.BuzzByte.utils.validation.GenericValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final GenericValidator<PostLike> postLikeValidator;
    private final PostRepository postRepository;
    @Override
    @Transactional
    public List<PostLike> getAllLikes() {
       try {
            return postLikeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving likes: " + e.getMessage());
        }
    }

    @Override
    public void deleteLike(Long likeId) {
        PostLike like = postLikeRepository.findById(likeId)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));

        try{
            Post post = like.getPost();
            post.setLikes(post.getLikes() - 1);
            postRepository.save(post);
            postLikeRepository.deleteById(likeId);

        } catch (Exception e) {
            throw new RuntimeException("Error deleting like: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<PostLike> getLikesByPost(Long postId) {
        try {
            return postLikeRepository.findPostLikeByPostId(postId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving likes: " + e.getMessage());
        }
    }

    @Override
    public PostLike addLike(PostLike like) {
       postLikeValidator.validate(like);
        try {
            Post post = like.getPost();
            post.setLikes(post.getLikes() + 1);
            System.out.println(post.getLikes());
            postRepository.save(post);
            return postLikeRepository.save(like);

        } catch (Exception e) {
            throw new RuntimeException("Error adding like: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public PostLike getLike(Long likeId) {
        return postLikeRepository.findById(likeId)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));
    }

    public boolean isPostLikedByUser(Long postId, Long userId) {
        return postLikeRepository.existsByPostIdAndUserId(postId, userId);
    }
}
