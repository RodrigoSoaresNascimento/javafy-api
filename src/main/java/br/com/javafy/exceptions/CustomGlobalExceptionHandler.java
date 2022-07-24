package br.com.javafy.exceptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @NotNull
    private ResponseEntity<Object> returnError(String exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", exception);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PessoaNaoCadastradaException.class)
    public ResponseEntity<Object> handleException(PessoaNaoCadastradaException exception,
                                                  HttpServletRequest request) {
        return returnError(exception.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception,
                                                  HttpServletRequest request) {
        return returnError(exception.getMessage());
    }

    @ExceptionHandler(PlaylistException.class)
    public ResponseEntity<Object> handleException(PlaylistException exception,
                                                  HttpServletRequest request) {
        return returnError(exception.getMessage());
    }

    @ExceptionHandler(SpotifyException.class)
    public ResponseEntity<Object> handleException(SpotifyException exception,
                                                  HttpServletRequest request) {
        return returnError(exception.getMessage());
    }

    @ExceptionHandler(SeguidoresException.class)
    public ResponseEntity<Object> handleException(SeguidoresException exception,
                                                  HttpServletRequest request) {
        return returnError(exception.getMessage());
    }
}
