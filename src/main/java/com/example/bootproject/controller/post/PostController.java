package com.example.bootproject.controller.post;

import com.example.bootproject.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
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
        long createResult = postService.createPost(dto,id);

        return createResult!=-1?ResponseEntity.created(new URI("http://localhost:8080/post/"+createResult)).build():ResponseEntity.noContent().build();
    }


}
