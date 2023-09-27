package com.example.bootproject.system.controlleradvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // 예외 처리 로직을 여기에 구현
        // 여기서는 간단히 예외 메시지를 반환하도록 함
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }
}
