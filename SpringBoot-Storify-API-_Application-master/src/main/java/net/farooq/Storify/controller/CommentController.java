package net.farooq.Storify.controller;

import net.farooq.Storify.entites.Comment;
import net.farooq.Storify.playload.CommentDto;
import net.farooq.Storify.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private CommentService   commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:5555/api/comment/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> createComment(@PathVariable("postId") long postId,
                                                @RequestBody CommentDto commentDto,
                                                BindingResult result) {

        if(result.hasErrors()){
            return new ResponseEntity<Object>(
                    result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/1/comments

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable ("postId")long postId,
            @PathVariable("id")long id,
            @RequestBody CommentDto commentDto) {

        CommentDto dto = commentService.updateComment(postId, id, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:5555/api/comment/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
    //http://localhost:8080/api/posts/1/comments
    @DeleteMapping("/posts/{postId}/comments/{id}")

    public ResponseEntity<String> deleteComment(
            @PathVariable("id")long id,
            @PathVariable( "postId")long postId
    ){
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }



    @GetMapping("/page")
    public ResponseEntity<List<Comment>> getPaginatedPosts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Comment> paginatedComment = commentService.getPaginatedComment(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(paginatedComment);
    }

}
