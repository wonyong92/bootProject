package com.example.bootproject.controller;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class LoginDto {
    @NotNull
    String Id;
    @NotNull
    String pwd;
}
