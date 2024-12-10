package com.example.BuzzByte.service;

import com.example.BuzzByte.model.comments.PostComment;
import com.example.BuzzByte.repository.PostCommentRepository;
import com.example.BuzzByte.utils.validation.GenericValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final GenericValidator<PostComment> postCommentValidator;

    //method to get all comments
    public List<PostComment> getAllComments() {
        try {
            return postCommentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving comments: " + e.getMessage());
        }
    }

    public void deleteCommentIfAuthorized(Long commentId, Long userId) {

        PostComment comment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // Check authorization-only the user who created the comment or the user who created the post can delete the comment
        if (! (comment.getUserId().equals(userId) ||
                comment.getPost().getUser().getId().equals(userId))) {
            throw new AccessDeniedException("You are not authorized to delete this comment.");
        }

        try{
            postCommentRepository.deleteById(commentId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting comment: " + e.getMessage());
        }
    }


    // Method to retrieve all comments for a specific post
    public List<PostComment> getCommentsByPost(Long postId) {
        try {
            return postCommentRepository.findPostCommentByPostId(postId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving comments: " + e.getMessage());
        }

    }

    // Method to add a new comment
    public PostComment addComment(PostComment comment) {

        if(comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        if(comment.getCreatedAt() == null) {
            comment.setCreatedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        }
        postCommentValidator.validate(comment);
        try {
            return postCommentRepository.save(comment);  // Save and return the new comment
        }
        catch (Exception e) {
            throw new RuntimeException("Error adding comment: " + e.getMessage());
        }
    }

    // Method to update an existing comment
    public PostComment updateCommentIfAuthorized(PostComment comment, Long userId) {


        var updatedComment = postCommentRepository.findById(comment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if(!updatedComment.getUserId().equals(userId)) {
            throw new AccessDeniedException("You are not authorized to delete this comment.");
        }

        updatedComment.setContent(comment.getContent());
        postCommentValidator.validate(updatedComment);

        try {
            return postCommentRepository.save(updatedComment);
        } catch (Exception e) {
            throw new RuntimeException("Error updating comment: " + e.getMessage());
        }

    }

    // Method to get a specific comment
    public PostComment getComment(Long commentId) {
        return postCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }


}
