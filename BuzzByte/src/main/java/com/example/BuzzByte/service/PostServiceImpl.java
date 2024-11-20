package com.example.BuzzByte.service;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.repository.PostRepository;
import com.example.BuzzByte.utils.validation.GenericValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        // check if tags is null
        if (updatedPost.getTags() != null) {
            updatedPost.getTags().clear();
            updatedPost.getTags().addAll(post.getTags());
        } else {
            updatedPost.setTags(post.getTags());
        }

        updatedPost.setUser(post.getUser());
        updatedPost.setImage(post.getImage());

        // check if comments is null
        if (updatedPost.getComments() != null) {
            updatedPost.getComments().clear();
            if (post.getComments() != null) {
                updatedPost.getComments().addAll(post.getComments());
            }
        } else {
            updatedPost.setComments(post.getComments() != null ? post.getComments() : new ArrayList<>());
        }

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
        // get the Post, initialize tags and comments
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) {
            throw new EntityNotFoundException(String.format("Post with id %d not found", postId));
        }
        // the below is done to avoid lazy initialization exception; could be done in the repository, somehow, dunno
        Hibernate.initialize(post.get().getTags());
        Hibernate.initialize(post.get().getComments());
        return post.get();
    }

    @Override
    @Transactional
    public Post getPost(String postTitle) {
        // TODO: fix lazy initialization exception
        return postRepository.findByTitle(postTitle).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post with title %s not found", postTitle))
        );
    }

    @Override
    @Transactional
    public List<Post> getAllPosts() {
        // ?: might be deprecated + possible lazy initialization exception
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Page<Post> getPosts(int pageNumber, int pageSize) {
        // ?: possible lazy initialization exception
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postRepository.findAll(pageable);    // works because PostRepository extends PagingAndSortingRepository via JpaRepository
    }

    @Override
    @Transactional
    public Page<Post> findAllByCriteria(Long postId, String postTitle, String postContent, String postAuthor, List<String> postTags, Pageable pageable) {
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
            if (postTags != null && !postTags.isEmpty()) {
                // Use Join for tags and filter by names
                Join<Object, Object> tagsJoin = root.join("tags");
                predicates.add(tagsJoin.get("name").in(postTags));
            }
            if(predicates.isEmpty() || query == null) return null;
            query.where(predicates.toArray(new Predicate[0]));
            return query.getRestriction();
        };
        Page<Post> found = postRepository.findAll(specification, pageable);
        found.forEach(post -> {
            Hibernate.initialize(post.getTags());
            Hibernate.initialize(post.getComments());
        });
        return found;
    }

   /* @Override
    @Transactional
    // deprecated; this was used to initialize tags and comments, now done in findAllByCriteria
    public Page<Post> findAllByCriteriaWrapper(Long postId, String postTitle, String postContent, String postAuthor, String postCategory, Pageable pageable) {
        Page<Post> posts = findAllByCriteria(postId, postTitle, postContent, postAuthor, postCategory, pageable);
        posts.forEach(post -> {
            Hibernate.initialize(post.getTags());
            Hibernate.initialize(post.getComments());
        });
        return posts;
    }*/
}
