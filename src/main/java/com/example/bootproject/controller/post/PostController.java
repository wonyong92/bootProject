package com.example.bootproject.controller.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.service.post.PostService;
import com.example.bootproject.vo.request.postCreateDto;
import com.example.bootproject.vo.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;


    @PostMapping("/create")
    public ResponseEntity<? extends Object> createPost(@ModelAttribute @Valid postCreateDto dto, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:\n");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            throw new Exception(errorMessage.toString());
        }

        String id = (String) session.getAttribute("id");
        if (id == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        long createResult = postService.createPost(dto, id);
        log.info("writer id {} created id : {}", id, createResult);
        return createResult != -1 ? ResponseEntity.created(new URI("http://localhost:8080/post/" + createResult)).build() : ResponseEntity.noContent().build();
    }

    @GetMapping("/read/{postId}")
    public ResponseEntity<? extends  Object> readPost(@PathVariable Integer postId){
        if(postId==null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Post findPost = postService.getPost(postId);
        log.info("findPost : {}",findPost);
        return new ResponseEntity<>(new PostResponseDto(findPost), HttpStatus.OK);
    }
}
