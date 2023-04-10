package com.autmaple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.Principal;

@RestControllerAdvice
public class HelloController {
    @GetMapping("/")
    public String hello(Principal principal) {
        return principal.getName();
    }
}
