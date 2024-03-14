package com.example.service;

import com.example.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long id,CommentDto commentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentsByPostId(long postId);

    List<CommentDto> getALlComments();

    CommentDto updateComment(long commentId, CommentDto commentDto);
}
