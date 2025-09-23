package com.example.lestock.controller.common;

import com.example.lestock.controller.dto.FieldErrorDTO;
import com.example.lestock.controller.dto.ResponseErrorDTO;
import com.example.lestock.exceptions.DuplicateRecordException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExcepionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<FieldErrorDTO> fieldErrorDTOS = fieldErrors
                .stream()
                .map(
                        fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage(), fe.getCode())
                ).toList();
        return new ResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                fieldErrorDTOS
                );
    }
    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleDuplicateRecordException(DuplicateRecordException e) {
        return new ResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                List.of()
        );
    }
}
