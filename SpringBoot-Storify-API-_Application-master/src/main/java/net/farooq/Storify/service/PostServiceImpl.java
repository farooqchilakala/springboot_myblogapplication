package net.farooq.Storify.service;

import net.farooq.Storify.entites.Post;
import net.farooq.Storify.exception.DataNotFoundedException;
import net.farooq.Storify.playload.PostDto;
import net.farooq.Storify.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;
   private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    //Insert Operation
    @Override
    public PostDto CreateAllPost(PostDto postDto) {
        //Converting dto to entity
        Post post = mapToEntity(postDto);
        Post newpost = postRepository.save(post);
        //Converting entity to dto
        PostDto postRepository = mapToDto(newpost);
        return postRepository;
    }
//Pagenation Method
    @Override
    public List<Post> getPaginatedPosts(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> pagedResult = postRepository.findAll(paging);
        return pagedResult.getContent();
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    @Override
    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new DataNotFoundedException("Post", "id", id));
        return post;
//        Optional<Post> post = postRepository.findById(id);
//        return post.orElse(null);
    }
    @Override
    public Post updatePost(Long id, Post post) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            post.setId(id);
            return postRepository.save(post);
        } else {
            return null;
        }
    }

    @Override
    public void deletePost(Long id) {
        Post user = postRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Post", "id", id));
        postRepository.delete(user);
    }

//        Optional<Post> existingPost = postRepository.findById(id);
//        if (existingPost.isPresent()) {
//            postRepository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }


    private PostDto mapToDto(Post newpost) {
        PostDto dto = mapper.map(newpost, PostDto.class);
//        PostDto dto = new PostDto();
//    dto.setId(newpost.getId());
//    dto.setContent(newpost.getContent());
//    dto.setDescription(newpost.getDescription());
//    dto.setTitle(newpost.getTitle());
        return dto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post map = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        post.setTitle(postDto.getTitle());
        return map;
    }


}
