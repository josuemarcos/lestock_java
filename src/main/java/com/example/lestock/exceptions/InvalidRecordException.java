package com.example.lestock.exceptions;

import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidRecordException extends RuntimeException {
    private final List<FieldErrorDTO> fieldErrors;
        public InvalidRecordException(String message) {
                super(message);
                this.fieldErrors = List.of();
            }
        public InvalidRecordException(String message, List<FieldErrorDTO> fieldErrors) {
                super(message);
                this.fieldErrors = fieldErrors;
            }
}
