package com.example.bootproject.system.controlleradvice;

import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex) {
        ex.printStackTrace();
        return "404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleException(Exception e) {
        e.printStackTrace();
        ModelAndView mav = new ModelAndView("generic-error");
        mav.addObject("message", "An error occurred: " + e.getMessage());
        return mav;
    }
}

