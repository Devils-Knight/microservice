package com.autmaple.controller;

import com.autmaple.model.utils.ErrorMessage;
import com.autmaple.model.utils.ResponseWrapper;
import com.autmaple.model.utils.RestErrorList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseWrapper> handleException(HttpServletRequest request, ResponseWrapper responseWrapper) {
        return ResponseEntity.ok(responseWrapper);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ResponseWrapper> handleIOException(HttpServletRequest request, RuntimeException e) {
        RestErrorList errorList = new RestErrorList(HttpStatus.NOT_ACCEPTABLE, new ErrorMessage(e.getMessage(), e.getMessage()));
        ResponseWrapper wrapper = new ResponseWrapper(null, Collections.singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorList);
        return ResponseEntity.ok(wrapper);
    }
}
