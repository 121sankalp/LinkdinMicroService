package com.example.postService.services;

import com.example.postService.dtos.PostCreateRequestDto;
import com.example.postService.dtos.PostDto;
import com.example.postService.entities.Post;
import com.example.postService.exceptions.ResourceNotFoundException;
import com.example.postService.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class PostService {
    private final PostRepository postRepository;

    private  final ModelMapper modelMapper ;
    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
       Post post = modelMapper.map(postCreateRequestDto, Post.class) ;
       post.setUserId(userId);
        postRepository.save(post) ;
       return  modelMapper.map(post , PostDto.class) ;
    }

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post not found with id"+id))  ;
        return modelMapper.map(post , PostDto.class) ;
    }

    public List<PostDto>  getAllPostOfUser(Long userId) {
        log.info("Calling get All Post of user by user id {}",userId);
        List<Post>postList = postRepository.findByUserId(userId) ;
        return postList.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList()) ;
    }
}
