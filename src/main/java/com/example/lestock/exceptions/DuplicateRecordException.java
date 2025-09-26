package com.example.lestock.exceptions;

import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class DuplicateRecordException extends RuntimeException {
    private final List<FieldErrorDTO> fieldErrors;

    public DuplicateRecordException(String message) {
        super(message);
        this.fieldErrors = List.of();
    }

    public DuplicateRecordException(String message, List<FieldErrorDTO> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

}
