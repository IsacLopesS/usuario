package com.isac.infrastructure.exceptions.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private  String error;



}
