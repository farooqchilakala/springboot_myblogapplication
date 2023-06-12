package net.farooq.Storify.service;

import net.farooq.Storify.entites.Post;
import net.farooq.Storify.playload.PostDto;

import java.util.List;

public interface PostService {
    PostDto CreateAllPost(PostDto postDto);
    List<Post> getPaginatedPosts(Integer pageNo, Integer pageSize, String sortBy);
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
}
