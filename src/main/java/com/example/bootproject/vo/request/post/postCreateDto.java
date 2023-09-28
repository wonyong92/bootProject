package com.example.bootproject.vo.request.post;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class postCreateDto {

    @NotBlank String title;
    String content;
    Integer parentId;

    private MultipartFile file1;
    private MultipartFile file2;

    //post, answer : create 용 맵핑 함수
    public Post dtoToEntity(Member member) {
        String name1 = file1 == null ? null : file1.getName();
        String name2 = file2 == null ? null : file2.getName();
        if (name1.equals(name2)) {
            name2.concat("(1)");
        }
        return new Post(title, content, member, parentId);
    }

    public List<MultipartFile> getFiles() {
        return List.of(file1, file2);
    }

    //post, answer : update 용 맵핑 함수
    public Post dtoToEntity(Member member, Integer postId) {
        String name1 = file1 == null ? null : file1.getName();
        String name2 = file2 == null ? null : file2.getName();
        if (name1.equals(name2)) {
            name2.concat("(1)");
        }
        return new Post(postId, title, content, member, parentId);
    }
}
