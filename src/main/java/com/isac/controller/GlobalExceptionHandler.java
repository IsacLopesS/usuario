package com.isac.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.isac.infrastructure.exceptions.ConflictException;
import com.isac.infrastructure.exceptions.ResourceNotFoundException;
import com.isac.infrastructure.exceptions.UnauthorizedException;
import com.isac.infrastructure.exceptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                            HttpServletRequest request){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getRequestURI(),
                "Not Found"
                ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleCOnflictException(ConflictException exception,
                                                                    HttpServletRequest request){
        //return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                request.getRequestURI(),
                "Email existente"
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException( UnauthorizedException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDTO buildError(int status, String mensagem, String path, String error)  {
       return  ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(mensagem)
                .path(path)
                .error(error)
                .build();
    }


}
