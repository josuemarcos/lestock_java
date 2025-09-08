package com.example.lestock.controller.common;

import com.example.lestock.controller.dto.FieldErrorDTO;
import com.example.lestock.controller.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExcepionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<FieldErrorDTO> fieldErrorDTOS = fieldErrors
                .stream()
                .map(
                        fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage())
                ).toList();
        return new ResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                fieldErrorDTOS
                );
    }
}
