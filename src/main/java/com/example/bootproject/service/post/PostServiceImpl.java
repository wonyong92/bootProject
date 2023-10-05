package com.example.bootproject.service.post;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.repository.post.PostRepository;
import com.example.bootproject.vo.request.post.postCreateDto;
import com.example.bootproject.vo.response.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Value("${multipart.upload.path}")
    private String uploadPath;

    @Override
    public long createPost(postCreateDto dto, String id) {
        log.info("ffff");
        Member member = memberRepository.findById(id).orElse(null);
        Post parent = null;
        if (member != null) {
            //답글에 다시 답글 생성 막기
            if (dto.getParentId() != null) {
                parent = postRepository.findById(dto.getParentId()).orElse(null);
                if (parent != null && parent.getParent() != null) {
                    return -1;
                }
            }
//            log.info(new PostResponseDto(parent).toString());
            List<String> names;
            try {

                names = uploadFile(dto.getFiles());

            } catch (IOException e) {
                log.info("file upload fail {} ", e.getMessage());
                return -1;
            }


            Post entity = dto.dtoToEntity(member, parent);
            try {
                entity.setFile1(names.get(0));
                entity.setFile2(names.get(1));
            } catch (Exception e) {

            }
            log.info("names{}",names.toString());
            postRepository.save(entity);

            return entity.getPostId();
        }
        return -1;
    }

    private List<String> uploadFile(List<MultipartFile> files) throws IOException {
        List<String> names = new ArrayList<>();

        if (files != null) {
            // Normalize the file name
            files.forEach(file -> {
                if (file != null && !file.isEmpty()) {
                    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

                    // Generate a unique file name based on the current timestamp
                    String uniqueFileName = generateUniqueFileName(originalFileName);

                    // Create the upload directory if it doesn't exist
                    Path directoryPath = Paths.get(uploadPath);
                    if (!Files.exists(directoryPath)) {
                        try {
                            Files.createDirectories(directoryPath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Copy the file to the target location with the unique file name
                    Path targetLocation = Paths.get(uploadPath).resolve(uniqueFileName);
                    try {
                        Files.copy(file.getInputStream(), targetLocation);
                        file.getInputStream().close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    names.add(uniqueFileName);

                }
            });
        }
        return names;
    }

    private String generateUniqueFileName(String originalFileName) {
        UUID uuid = UUID.randomUUID();

        int lastIndex = originalFileName.lastIndexOf('.');
        String extension = "";
        String fileName;

        if (lastIndex != -1) {
            extension = originalFileName.substring(lastIndex);
            fileName = originalFileName.substring(0, lastIndex);
        } else {
            fileName = originalFileName;
        }

        return fileName + "_" + uuid + extension;
    }

    @Override
    public Post getPost(Integer postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAllDto(pageable);
    }

    @Override
    public long updatePost(postCreateDto dto, String id, Integer postId) {
        Member member = memberRepository.findById(id).orElse(null);
        Post post = postRepository.findByPostIdAndWriter_MemberId(postId, id).orElse(null);

        if (member != null && post != null) {

            List<String> names;
            try {
                names = uploadFile(dto.getFiles());
            } catch (IOException e) {
                log.info("file upload fail {} ", e.getMessage());
                return -1;
            }

            Post entity = dto.dtoToEntity(member, post.getParent());
            entity.setScore(post.getScore());
            try {
                entity.setFile1(names.get(0));
                entity.setFile2(names.get(1));
            }catch (Exception e){

            }
            post.setFile1(entity.getFile1());
            post.setFile2(entity.getFile2());
            post.setContent(entity.getContent());
            post.setTitle(entity.getTitle());
            postRepository.save(post);
            return post.getPostId();
        }
        return -1;
    }

    @Override
    public Page<PostResponseDto> findAllByMemberId(Pageable pageable, String memberId) {
        return postRepository.findAllDtoByMemberId(pageable, memberId);
    }

    @Override
    public boolean deletePost(String id, Integer postId) {
        Member member = memberRepository.findById(id).orElse(null);
        Post post = postRepository.findByPostIdAndWriter_MemberId(postId, id).orElse(null);
        if (member != null && post != null) {
            return postRepository.deleteByIdAndCheckSuc(postId);
        }
        return false;
    }

    @Override
    public List<Post> getPostsByParentId(Integer parentId) {
        return postRepository.findByParent_PostId(parentId);
    }

    public Resource loadFileAsResource(Integer postId, int num) {
        String filePath = "uploads/";
        AtomicReference<Resource> result = new AtomicReference<>();
        log.info("postId {} , num {}", postId, num);
        postRepository.findById(postId).ifPresent(post -> {
            try {
                Path filePathPath = Paths.get(filePath + (num == 1 ? post.getFile1() : post.getFile2())).normalize();
                log.info("filePath {} ", filePath);
                Resource resource = new UrlResource(filePathPath.toUri());
                if (resource.exists()) {
                    result.set(resource);
                } else {
                    result.set(null);
                }
            } catch (Exception ex) {
                result.set(null);
            }
        });
        return result.get();
    }

    @Override
    public Page<PostResponseDto> findByTitleContaining(String titleInput, Pageable pageable) {
        return postRepository.titleSearch(titleInput, pageable);

    }
}
