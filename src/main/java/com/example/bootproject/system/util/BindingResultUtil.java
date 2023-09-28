package com.example.bootproject.system.util;

import org.springframework.validation.BindingResult;

public class BindingResultUtil {
    private BindingResultUtil() {
    }
    public static void extracted(BindingResult result) throws Exception {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:\n");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            throw new Exception(errorMessage.toString());
        }
    }
}
