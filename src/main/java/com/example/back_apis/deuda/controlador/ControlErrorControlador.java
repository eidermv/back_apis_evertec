package com.example.back_apis.deuda.controlador;

import com.example.back_apis.deuda.modelo.Respuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControlErrorControlador {

    private Respuesta result;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeException(RuntimeException e) {
        result = new Respuesta(-100, "[Exception Response] - Exception: " + e.getMessage(), null);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {
        result = new Respuesta(-100, "[Exception Response] - Exception: " + e.getMessage(), null);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
