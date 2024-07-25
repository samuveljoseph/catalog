package com.ecommerce.catalog.business.Exception;

import com.ecommerce.catalog.business.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<ApiResponse<String>> handleValidationExceptions(
      WebExchangeBindException ex) {
    String errorMessage =
        ex.getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));
    ApiResponse<String> response = new ApiResponse<>("Error",null, errorMessage);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleExceptions(Exception ex) {
    ApiResponse<String> response = new ApiResponse<>("error",null, ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Mono<ApiResponse<Void>> handleCategoryNotFoundException(BusinessException ex) {
    return Mono.just(new ApiResponse<>("error",null, ex.getMessage()));
  }
}
