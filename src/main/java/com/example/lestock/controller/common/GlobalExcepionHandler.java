package com.example.lestock.controller.common;
import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.controller.dto.errors.ResponseErrorDTO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.exceptions.InvalidRecordException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
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
                e.getFieldErrors()
        );
    }

    @ExceptionHandler(InvalidRecordException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleInvalidRecordException(InvalidRecordException e) {
        return new ResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                e.getFieldErrors()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        List<FieldErrorDTO> fields = List.of();

        Throwable cause = e.getCause();
        if(cause instanceof InvalidFormatException ife) {
            if(ife.getTargetType().isEnum()) {
                String fieldName = ife.getPath().isEmpty()
                        ? "unknown" : ife.getPath().getFirst().getFieldName();
                String allowedValues = Arrays.toString(ife.getTargetType().getEnumConstants());

                String message = String.format("Invalid value '%s' for field '%s'. Allowed values: '%s",
                        ife.getValue(), fieldName, allowedValues);
                fields = List.of(
                        new FieldErrorDTO(fieldName, message, "InvalidValue")
                );
            }
        }
        return new ResponseErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                fields);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseErrorDTO handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseErrorDTO(HttpStatus.UNAUTHORIZED.value(), "Invalid user name or password", null);
    }




}
