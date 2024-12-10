package com.example.BuzzByte.service;

import com.example.BuzzByte.model.comments.PostComment;


import java.util.List;

public interface PostCommentService {

    List<PostComment> getAllComments();
    void deleteCommentIfAuthorized(Long commentId, Long userId);
    List<PostComment> getCommentsByPost(Long postId);
    PostComment addComment(PostComment comment);
    PostComment updateCommentIfAuthorized(PostComment comment, Long userId);
    PostComment getComment(Long commentId);
}
