package net.farooq.Storify.service;

import net.farooq.Storify.entites.Comment;
import net.farooq.Storify.entites.Post;
import net.farooq.Storify.exception.DataNotFoundedException;
import net.farooq.Storify.exception.StorifyBlogApiException;
import net.farooq.Storify.playload.CommentDto;
import net.farooq.Storify.playload.CommentResponse;
import net.farooq.Storify.repository.CommentRepository;
import net.farooq.Storify.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements  CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Comment> getPaginatedComment(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Comment> pagedResult = commentRepository.findAll(paging);
        return pagedResult.getContent();
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new DataNotFoundedException("Post", "id", postId));
        comment.setPost(post);
        //Once exception happing here it will stop here only and then it give exception commit
        Comment newComment =  commentRepository.save(comment);
        CommentDto dto =mapToDTO(newComment);
        return dto;
    }

    private CommentDto mapToDTO(Comment newComment) {
        CommentDto Dto = mapper.map(newComment, CommentDto.class);
        return Dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);

        return comment;
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new DataNotFoundedException("post", "id", postId));

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new DataNotFoundedException("comment", "id", id));

        if (comment.getPost().getId() != post.getId()) {
            throw new StorifyBlogApiException(HttpStatus.BAD_REQUEST, "Post not matched");
        }
        comment.setId(id);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment newComment = commentRepository.save(comment);

        return  mapToDTO(newComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        // convert list of comment entities to list of comment dto's
        return comments.stream().map(comment ->
                mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new DataNotFoundedException("post", "id", postId));

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new DataNotFoundedException("comment", "id", id));

        if (comment.getPost().getId() != post.getId()) {
            throw new StorifyBlogApiException(HttpStatus.BAD_REQUEST, "Post not matched");
        }
        commentRepository.deleteById(id);
    }

    }

//    @Override
//    public CommentDto createAllComment(CommentDto commentDto) {
//        //Convert dto to entity
//        Comment comment = mapToEntity(commentDto);
//        Comment newcommentuser =commentRepository.save(comment);
//        //Convert entity to dto
//        CommentDto dto = mapToDto(newcommentuser);
//        return dto;
//    }
//
//    @Override
//    public Comment getcommentbyid(long id) {
//        Comment com = commentRepository.findById(id).
//                orElseThrow(() -> new DataNotFoundedException("Comment", "id", id));
//        return com;
//    }
//
//    @Override
//    public CommentDto updatecommentbyid(CommentDto commentDto, long id) {
//        Comment existingUser = commentRepository.findById(id).orElseThrow(
//                ()-> new DataNotFoundedException("Comment", "id", id));
//        existingUser.setBody(commentDto.getBody());
//        existingUser.setEmail(commentDto.getEmail());
//        existingUser.setName(commentDto.getName());
//        Comment updatedUser = commentRepository.save(existingUser);
//        return mapToDto(updatedUser);
//    }
//
//    @Override
//    public void deletePostById(long id) {
//        Comment user = commentRepository.findById(id).orElseThrow(() ->
//                new DataNotFoundedException("Post", "id", id));
//        commentRepository.delete(user);
//    }
//
//    @Override
//    public List<Comment> getallcomments() {
//        return commentRepository.findAll();
//    }
//
//    @Override
//    public List<Comment> getPaginatedComment(Integer pageNo, Integer pageSize, String sortBy) {
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<Comment> pagedResult = commentRepository.findAll(paging);
//        return pagedResult.getContent();
//    }
//
////    @Override
////    public CommentResponse getAllcomments(int pageNo, int pageSize, String sortBy, String sortDir) {
////        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
////                : Sort.by(sortBy).descending();
////
////        // create Pageable instance
////        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
////        Page<Comment> posts = commentRepository.findAll(pageable);
////        // get content for page object
////        List<Comment> listOfPosts = posts.getContent();
////        List<CommentDto> content= listOfPosts.stream().
////                map(Comment ->  mapToDto(Comment)).collect(Collectors.toList());
////        CommentResponse commentResponse = new CommentResponse();
////        commentResponse.setContent(content);
////        commentResponse.setPageNo(posts.getNumber());
////        commentResponse.setPageSize(posts.getSize());
////        commentResponse.setTotalElements(posts.getTotalElements());
////        commentResponse.setTotalPages(posts.getTotalPages());
////        commentResponse.setLast(posts.isLast());
////        return commentResponse;
//
//    private CommentDto mapToDto(Comment newcommentuser) {
//        CommentDto m = mapper.map(newcommentuser, CommentDto.class);
//        return m;
//    }
//    private Comment mapToEntity(CommentDto commentDto) {
//        Comment map = mapper.map(commentDto, Comment.class);
//        return map;
//    }
//
//}
