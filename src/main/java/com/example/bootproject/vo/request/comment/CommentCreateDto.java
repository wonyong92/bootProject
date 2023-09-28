package com.example.bootproject.vo.request.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data

public class CommentCreateDto {

    @NotBlank(message = "Content is required")
    private String content;

}
