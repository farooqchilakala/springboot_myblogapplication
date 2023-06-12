package net.farooq.Storify.controller;

import net.farooq.Storify.entites.Post;
import net.farooq.Storify.playload.PostDto;
import net.farooq.Storify.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    private ModelMapper mapper;

    public PostController(PostService postService, ModelMapper mapper) {
        this.postService = postService;
        this.mapper = mapper;
    }

    //http://localhost:5555/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> CreateAllPost(@RequestBody PostDto postDto ){
        return new ResponseEntity<PostDto>(postService.
                CreateAllPost( postDto), HttpStatus.CREATED);
    }

    //http://localhost:5555/api/posts
        @GetMapping
        public List<Post> getAllPosts() {
            return postService.getAllPosts();
        }
    //http://localhost:5555/api/posts/1
   // @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/{id}")
        public ResponseEntity<Post> getPostById(@PathVariable Long id) {
            Post post = postService.getPostById(id);
            if (post != null) {
                return ResponseEntity.ok(post);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    //http://localhost:5555/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}")
        public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
            Post updatedPost = postService.updatePost(id, post);
            if (updatedPost != null) {
                return ResponseEntity.ok(updatedPost);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    //http://localhost:5555/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deletePost(@PathVariable(value = "id") Long id) {
            postService.deletePost(id);
            return new ResponseEntity<>("Post Entity deleted successfully",HttpStatus.OK);
//            boolean deleted = postService.deletePost(id);
//            if (deleted) {
//                return ResponseEntity.noContent().build();
//            } else {
//                return ResponseEntity.notFound().build();
//            }
            //http://localhost:5555/api/posts/page?pageNo=0&pageSize=3
        }
    //http://localhost:5555/api/posts/page?pageNo={pageNo}&pageSize={pageSize}

        @GetMapping("/page")
        public ResponseEntity<List<Post>> getPaginatedPosts(
                @RequestParam(defaultValue = "0") Integer pageNo,
                @RequestParam(defaultValue = "10") Integer pageSize,
                @RequestParam(defaultValue = "id") String sortBy) {
            List<Post> paginatedPosts = postService.getPaginatedPosts(pageNo, pageSize, sortBy);
            return ResponseEntity.ok(paginatedPosts);
        }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
//        postService.deletePostById(id);
//        return new ResponseEntity<>("User entity deleted successfully.", HttpStatus.OK);
//
//    }
    }


