package com.example.bootproject.vo.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class LoginRequestDto {
    @NotNull
    String Id;
    @NotNull
    String pwd;
}
