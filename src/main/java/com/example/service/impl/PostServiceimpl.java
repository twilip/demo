package com.example.service.impl;

import com.example.entity.Post;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.PostDto;
import com.example.repository.PostRepository;
import com.example.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class PostServiceimpl implements PostService {
    private PostRepository postRepo;

    public PostServiceimpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post=new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post save = postRepo.save(post);
        PostDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found")
        );
        postRepo.deleteById(id);
    }

    @Override
    public List<PostDto> getALlPost(int pageNo, int pageSize, String sortDir, String sortBy) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy):Sort.by(sortBy);
        Pageable pageable=PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pagePost = postRepo.findAll(pageable);
        List<Post> post = pagePost.getContent();
        List<PostDto> postDto = post.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public PostDto updatePost(long id,PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found")
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post save = postRepo.save(post);
        PostDto dto = mapToDto(save);
        return dto;
    }


    PostDto mapToDto(Post post){
        PostDto dto=new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

}
