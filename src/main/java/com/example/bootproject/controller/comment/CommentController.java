package com.example.bootproject.controller.comment;

import com.example.bootproject.entity.comment.Comment;
import com.example.bootproject.service.comment.CommentService;
import com.example.bootproject.system.util.BindingResultUtil;
import com.example.bootproject.system.util.SessionUtil;
import com.example.bootproject.vo.request.comment.CommentCreateDto;
import com.example.bootproject.vo.response.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {


    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") Long id) {
        Comment comment = commentService.getCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createComment(@Valid @ModelAttribute CommentCreateDto commentCreateDto, BindingResult bindingResult, HttpSession session, @RequestParam(required = false) Integer postId) throws Exception {
        BindingResultUtil.extracted(bindingResult);
        String id = SessionUtil.getLoginId(session);
        Comment comment = commentService.createComment(commentCreateDto, id, postId);

        return comment != null ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update/{commentId}")
    public ResponseEntity<String> updateComment(@Valid @ModelAttribute CommentCreateDto commentCreateDto, BindingResult bindingResult, HttpSession session, @PathVariable Long commentId) throws Exception {
        // Check for validation errors
        BindingResultUtil.extracted(bindingResult);
        String writerId = SessionUtil.getLoginId(session);

        if (commentId == null || commentId < 1l || (commentService.updateComment(commentCreateDto, writerId, commentId)) == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long commentId, HttpSession session) {
        String writerId = SessionUtil.getLoginId(session);

        if (commentId == null || commentId < 1l || !commentService.deleteComment(commentId,writerId)) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/read/post/{postId}")
    public ResponseEntity<? extends Object> getCommentsByPostId(@PathVariable Integer postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments.stream().map(CommentResponseDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/read/writer/{writerId}")
    public ResponseEntity<? extends Object> getCommentsByWriterId(@PathVariable String writerId) {
        List<Comment> comments = commentService.getCommentsByWriterId(writerId);
        return new ResponseEntity<>(comments.stream().map(CommentResponseDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }
}

