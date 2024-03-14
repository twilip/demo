package com.example.controller;

import com.example.payload.PostDto;
import com.example.service.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto Dto = postService.createPost(postDto);
return new ResponseEntity<>(Dto,HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getALlPost( @RequestParam(name = "pageNo",defaultValue = "0",required = false)int pageNo,
                                                     @RequestParam(name = "pageSize",defaultValue = "3",required = false)int pageSize ,
                                                     @RequestParam(name = "sortBy",defaultValue = "id",required = false)String sortBy,
                                                     @RequestParam(name = "sortDir",defaultValue = "desc",required = false)String sortDir
                                                     ){

        List<PostDto > postDtos=  postService.getALlPost(pageNo,pageSize,sortDir,sortBy);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);


    }
@PreAuthorize(("hasrole('ADMIN')"))
    @PutMapping
    private ResponseEntity<PostDto> updatePost(@RequestParam("id")long id,@RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


}
