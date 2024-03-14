package com.example.controller;

import com.example.payload.CommentDto;
import com.example.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}")
        public ResponseEntity<CommentDto> createComment(@PathVariable long id,@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(id,commentDto), HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId){
     List<CommentDto> dto=   commentService.getCommentsByPostId(postId);
     return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAll(){
        List<CommentDto> dto=   commentService.getALlComments();
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping
    public  ResponseEntity<CommentDto> updateComment(@RequestParam("commentId")long commentId,CommentDto commentDto){
     CommentDto dto=  commentService.updateComment(commentId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }

public void get(){}
}
