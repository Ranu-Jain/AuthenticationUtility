package com.neo.client.project.exceptionhandlers;

import com.neo.client.project.exceptions.BaseSecurityException;
import com.neo.client.project.exceptions.BaseException;
import com.neo.client.project.exceptions.ValidationException;
import com.neo.client.project.model.response.ErrorDto;
import com.neo.client.project.model.response.ResponseDto;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseSecurityException.class)
    public ResponseEntity<Object> handleSecurityException(BaseSecurityException ex) {
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage())
                .build();
        return generateResponseWithErrors(List.of(errorDto));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleCoffeeException(BaseException ex) {
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage())
                .build();
        return generateResponseWithErrors(List.of(errorDto));
    }


    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<Object> handleJwtException(JwtException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();
        logger.error("Error in Authorization ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseDto.builder()
                        .status(1)
                        .errors(List.of(errorDto))
                        .build());
    }

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {

        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseDto.builder()
                        .status(1)
                        .errors(List.of(errorDto))
                        .build());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();
        logger.error("Error in handleGenericException ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(
                ResponseDto.builder()
                        .status(1)
                        .errors(List.of(errorDto))
                        .build());
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();
        logger.error("Error in handleConflict ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.CONFLICT.value()))
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseDto.builder()
                        .status(1)
                        .errors(List.of(errorDto))
                        .build());
    }
    private ResponseEntity<Object> generateResponseWithErrors(List<ErrorDto> errors) {
        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .status(1)
                        .errors(errors)
                        .build());
    }
}
