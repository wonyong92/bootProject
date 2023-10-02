package com.example.bootproject.controller.rest.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.repository.post.PostRepository;
import com.example.bootproject.service.post.PostService;
import com.example.bootproject.system.util.SessionUtil;
import com.example.bootproject.vo.request.post.postCreateDto;
import com.example.bootproject.vo.response.post.PostResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.example.bootproject.system.util.BindingResultUtil.extracted;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "게시글", description = "게시글 관련 api 입니다.")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @Operation(summary = "게시글 / 답글 생성 API", description = "post 생성 메서드")
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<? extends Object> createPost(@ModelAttribute @Valid postCreateDto dto, BindingResult result, HttpSession session) throws Exception {
        //질문글 생성 API 에 답글 데이터 요청 방지

        if (dto.getParentId() != null) {
            return ResponseEntity.badRequest().build();
        }
        extracted(result);

        String id = SessionUtil.getLoginId(session);

        long createResult = postService.createPost(dto, id);
        log.info("writer id {} created id : {}", id, createResult);
        return createResult != -1 ? ResponseEntity.ok(createResult) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/read/{postId}")
    public String readPost(@PathVariable Integer postId, Model model, HttpServletResponse httpServletResponse) {
        if (postId == null) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return "main";
        }

        Post findPost = postService.getPost(postId);
        //toString() 과정에서 세션 종료 문제 발생
        // 해결방법 1. 컨트롤러까지 트랜잭션 연결
        // 해결방법 2. 그냥 필요한 필드만 dto로 뽑아내기
        // 해결방법 3. toString()을 필요한 필드로 직접 구현
        log.info("findPost : {}", new PostResponseDto(findPost));
        model.addAttribute("post", new PostResponseDto(findPost));
        return "pageInfo";
    }

    @GetMapping({""})//스프링 부트에서는 /를 자동으로 제외하지 않으므로 사용 주의
    @ResponseBody
    public ResponseEntity<? extends Object> listPosts(Model model, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "id,desc") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]);  // 페이지당 10개씩 표시
        Page<PostResponseDto> postPage = postService.findAll(pageable);

        return ResponseEntity.ok(postPage);
    }

    @GetMapping({"/{memberId}"})//스프링 부트에서는 /를 자동으로 제외하지 않으므로 사용 주의
    @ResponseBody
    public ResponseEntity<? extends Object> listPostsByMemberId(Model model, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "id,desc") String sort, @PathVariable(required = false) String memberId) {
        if (memberId == null || memberId.trim() == "") {
            return ResponseEntity.noContent().build();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]);  // 페이지당 10개씩 표시
        Page<PostResponseDto> postPage = postService.findAllByMemberId(pageable, memberId);

        return ResponseEntity.ok(postPage);
    }

    @PostMapping("/update/{postId}")
    @ResponseBody
    public ResponseEntity<? extends Object> createPost(@ModelAttribute @Valid postCreateDto dto, BindingResult result, @PathVariable Integer postId, HttpSession session) throws Exception {

        extracted(result);

        String id = SessionUtil.getLoginId(session);

        long createResult = postService.updatePost(dto, id, postId);
        log.info("writer id {} updated post id : {}", id, createResult);
        return createResult != -1 ? ResponseEntity.ok(createResult) : ResponseEntity.noContent().build();
    }

    @GetMapping("/delete/{postId}")
    @ResponseBody
    public ResponseEntity<? extends Object> deletePost(@PathVariable Integer postId, HttpSession session) throws URISyntaxException {
        String id = SessionUtil.getLoginId(session);

        boolean createResult = postService.deletePost(id, postId);
        log.info("writer id {} updated post id : {}", id, createResult);
        return createResult ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/answers")
    @ResponseBody
    public ResponseEntity<? extends Object> getPostsByParentId(@RequestParam Integer parentId) {
        if (parentId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.getPostsByParentId(parentId).stream().map(PostResponseDto::new).collect(Collectors.toList()));
    }

    @PostMapping("/create/answer")
    @ResponseBody
    public ResponseEntity<? extends Object> createAnswerPost(@ModelAttribute @Valid postCreateDto dto, BindingResult result, HttpSession session) throws Exception {

        if (dto.getParentId() == null) {
            return ResponseEntity.badRequest().build();
        }

        extracted(result);

        String id = SessionUtil.getLoginId(session);

        long createResult = postService.createPost(dto, id);
        log.info("writer id {} created id : {}", id, createResult);
        return createResult != -1 ? ResponseEntity.created(new URI("http://localhost:8080/post/" + createResult)).build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestParam Integer postId, @RequestParam(defaultValue = "1") int fileNum) throws IOException {

        Resource resource = postService.loadFileAsResource(postId, fileNum);
        if (resource == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resource.getFilename().split("_|\\.")[0] + '.' + resource.getFilename().split("_|\\.")[2], StandardCharsets.UTF_8) + "\"")
                .body(resource);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<? extends Object> searchPostsByTitle(@RequestParam("title") String titleInput, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
        // Using the PostRepository to search for posts by title
        Pageable pageable = PageRequest.of(page, size);  // 페이지당 10개씩 표시
        Page<PostResponseDto> postList = postService.findByTitleContaining(titleInput, pageable);
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/test")
    @ResponseBody
    public Object test() {
        return postRepository.findByParentIsNull();
    }

    @GetMapping("/update/{postId}")
    public String updatePost(Model model, @PathVariable Integer postId, HttpSession session) throws Exception {
        if (session != null && session.getAttribute("id") != null) {
            Post findPost = postService.getPost(postId);
            log.info("{}", findPost.getWriter().getMemberId().equals(session.getAttribute("id")));
            if (findPost.getWriter().getMemberId().equals(session.getAttribute("id"))) {
                model.addAttribute("post", findPost);
                model.addAttribute("edit", true);
                return "pageWrite";
            }
        }
        throw new Exception("Not Authorized Access");
    }

    @GetMapping("/create")
    public String createPostView(Model model,HttpSession session) throws Exception {
        model.addAttribute("edit", false);
        return "pageWrite";
    }

}
