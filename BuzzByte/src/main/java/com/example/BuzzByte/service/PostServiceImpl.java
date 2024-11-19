package com.example.BuzzByte.service;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.repository.PostRepository;
import com.example.BuzzByte.utils.validation.GenericValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final GenericValidator<Post> postValidator;

    @Override
    public Post addPost(Post post) {
        try {
            // if post is null, throw exception
            if (post == null) {
                throw new IllegalArgumentException("Post cannot be null");
            }
            // if createdAt is not set, set it to current time
            if (post.getCreatedAt() == null) {
                post.setCreatedAt(Instant.ofEpochSecond(System.currentTimeMillis()));
            }
            // validate
            postValidator.validate(post);
            return postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("Error adding post: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Post updatePost(Post post) {
        var updatedPost = postRepository.findById(post.getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post with id %d not found", post.getId()))
        );

        updatedPost.setTitle(post.getTitle());
        updatedPost.setDescription(post.getDescription());
        updatedPost.setContent(post.getContent());
        updatedPost.getTags().clear();
        updatedPost.getTags().addAll(post.getTags());
        updatedPost.setUser(post.getUser());
        updatedPost.setImage(post.getImage());
        updatedPost.getComments().clear();
        updatedPost.getComments().addAll(post.getComments());
        updatedPost.setLikes(post.getLikes());
        updatedPost.setCreatedAt(post.getCreatedAt());
        try{
            postValidator.validate(updatedPost);
            return postRepository.save(updatedPost);
        } catch (Exception e) {
            throw new RuntimeException("Error updating post: " + e.getMessage());
        }
    }

    @Override
    public void deletePost(long postId) {
        try {
            postRepository.deleteById(postId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting post: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post with id %d not found", postId))
        );
    }

    @Override
    @Transactional
    public Post getPost(String postTitle) {
        return postRepository.findByTitle(postTitle).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post with title %s not found", postTitle))
        );
    }

    @Override
    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Page<Post> getPosts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postRepository.findAll(pageable);    // works because PostRepository extends PagingAndSortingRepository via JpaRepository
    }

    @Override
    @Transactional
    public Page<Post> findAllByCriteria(Long postId, String postTitle, String postContent, String postAuthor, String postCategory, Pageable pageable) {
        // use specification and criteria builder
        Specification<Post> specification = (root, query, criteriaBuilder) -> {
            // predicates for partial search
            List<Predicate> predicates = new ArrayList<>();
            if (postId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), postId));
            }
            if (postTitle != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + postTitle + "%"));
            }
            if (postContent != null) {
                predicates.add(criteriaBuilder.like(root.get("content"), "%" + postContent + "%"));
            }
            if (postAuthor != null) {
                predicates.add(criteriaBuilder.like(root.get("user").get("username"), "%" + postAuthor + "%"));
            }
            if (postCategory != null) {
                predicates.add(criteriaBuilder.like(root.get("tags").get("name"), "%" + postCategory + "%"));
            }
            if(predicates.isEmpty() || query == null) return null;
            query.where(predicates.toArray(new Predicate[0]));
            return query.getRestriction();
        };
    return postRepository.findAll(specification, pageable);
    }
}
