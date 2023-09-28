package com.example.bootproject.controller.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.service.post.PostService;
import com.example.bootproject.vo.request.postCreateDto;
import com.example.bootproject.vo.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

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
        //toString() 과정에서 세션 종료 문제 발생
        // 해결방법 1. 컨트롤러까지 트랜잭션 연결
        // 해결방법 2. 그냥 필요한 필드만 dto로 뽑아내기
        // 해결방법 3. toString()을 필요한 필드로 직접 구현
        log.info("findPost : {}",new PostResponseDto(findPost));
        return new ResponseEntity<>(new PostResponseDto(findPost), HttpStatus.OK);
    }

    @GetMapping({""})//스프링 부트에서는 /를 자동으로 제외하지 않으므로 사용 주의
    public ResponseEntity<? extends Object> listPosts(Model model, @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "id,desc") String sort ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]);  // 페이지당 10개씩 표시
        Page<PostResponseDto> postPage = postService.findAll(pageable);

        return ResponseEntity.ok(postPage);
    }

    @GetMapping({"/{memberId}"})//스프링 부트에서는 /를 자동으로 제외하지 않으므로 사용 주의
    public ResponseEntity<? extends Object> listPostsByMemberId(Model model, @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "id,desc") String sort ,@PathVariable(required = false) String memberId) {
        if(memberId == null || memberId.trim()==""){
            return ResponseEntity.noContent().build();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]);  // 페이지당 10개씩 표시
        Page<PostResponseDto> postPage = postService.findAllByMemberId(pageable,memberId);

        return ResponseEntity.ok(postPage);
    }

    @PostMapping("/update/{postId}")
    public ResponseEntity<? extends Object> createPost(@ModelAttribute @Valid postCreateDto dto, BindingResult result, @PathVariable Integer postId, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:\n");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            throw new Exception(errorMessage.toString());
        }

        String id = (String) session.getAttribute("id");
        if (id == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        long createResult = postService.updatePost(dto, id,postId);
        log.info("writer id {} updated post id : {}", id, createResult);
        return createResult != -1 ? ResponseEntity.created(new URI("http://localhost:8080/post/" + createResult)).build() : ResponseEntity.noContent().build();
    }

    @GetMapping("/delete/{postId}")
    public ResponseEntity<? extends Object> deletePost(@PathVariable Integer postId, HttpSession session) throws URISyntaxException {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        boolean createResult = postService.deletePost(id,postId);
        log.info("writer id {} updated post id : {}", id, createResult);
        return createResult ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
