package com.br.emakers.apiProjeto.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice; // <-- IMPORT NECESSÁRIO PARA @RestControllerAdvice


@RestControllerAdvice(basePackages = "com.br.emakers.apiProjeto.controller")
public class GlobalExceptionHandler {

    // Manipula exceções de Recurso Não Encontrado (404 Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.NOT_FOUND.value());
        errorDetails.put("error", "Not Found");
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", ""); // Pode ser preenchido com a URL da requisição se tiver RequestContextHolder

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Manipula exceções de Regra de Negócio (400 Bad Request)
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRuleException(BusinessRuleException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", "Bad Request");
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", ""); // Pode ser preenchido com a URL da requisição

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Manipula exceções de validação (@Valid) - Retorna 400 Bad Request com detalhes dos campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Validation Failed");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });
        errors.put("fieldErrors", fieldErrors);
        errors.put("message", "Um ou mais campos estão inválidos."); // Mensagem geral

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Manipula outras exceções genéricas não capturadas (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
        errorDetails.put("details", ex.getMessage()); // Para debug em ambiente de dev
        errorDetails.put("path", "");

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}