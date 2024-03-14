package com.example.service.impl;

import com.example.entity.Comment;
import com.example.entity.Post;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.CommentDto;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import com.example.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepo;
    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found")
        );

        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        Comment save = commentRepo.save(comment);
        CommentDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment5 not found")
        );
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> dto = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public List<CommentDto> getALlComments() {
        List<Comment> comments = commentRepo.findAll();
        List<CommentDto> dto = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());

        return dto;
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto commentDto) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment5 not found")
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment save = commentRepo.save(comment);
        CommentDto dto = mapToDto(save);
        return dto;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto=new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }




}
