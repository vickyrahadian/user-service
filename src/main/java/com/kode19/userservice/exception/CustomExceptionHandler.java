package com.kode19.userservice.exception;

import com.kode19.userservice.exception.customexception.ErrorDataProcessingException;
import com.kode19.userservice.exception.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static com.kode19.userservice.exception.ErrorCode.INTERNAL_ERROR;
import static com.kode19.userservice.exception.ErrorCode.INVALID_DATA;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @Value("${message.exception.general}")
    private String INTERNAL_ERROR_MESSAGE;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage());
        List<String> message = List.of(INTERNAL_ERROR_MESSAGE);
        ErrorResponseDTO error = new ErrorResponseDTO(INTERNAL_SERVER_ERROR.value(), message, INTERNAL_ERROR, request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handlePSQLException(PSQLException ex, HttpServletRequest request) {
        List<String> message = List.of(ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(BAD_REQUEST.value(), message, INVALID_DATA, request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ErrorDataProcessingException.class)
    public ResponseEntity<Object> handleErrorDataProcessingException(ErrorDataProcessingException ex, HttpServletRequest request) {
        List<String> message = List.of(ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(BAD_REQUEST.value(), message, INVALID_DATA, request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .map(String::toUpperCase)
                .toList();

        ErrorResponseDTO error = new ErrorResponseDTO(BAD_REQUEST.value(), errors, INVALID_DATA, request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }
}


