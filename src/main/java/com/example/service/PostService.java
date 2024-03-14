package com.example.service;

import com.example.payload.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    void deletePost(long id);

    List<PostDto> getALlPost(int pageNo, int pageSize, String sortDir, String sortBy);

    PostDto updatePost(long id,PostDto postDto);
}
