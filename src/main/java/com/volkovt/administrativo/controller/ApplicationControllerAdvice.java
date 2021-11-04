package com.volkovt.administrativo.controller;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.volkovt.administrativo.dto.ApiErrors;
import com.volkovt.administrativo.exception.RegraNegocioExcecao;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioExcecao.class)
    public ResponseEntity<ApiErrors> handleRegraNegocioExcecao (RegraNegocioExcecao rne) {
        String mensagemErro = rne.getMessage();
        HttpStatus status = rne.getStatus();
        return ResponseEntity.status(status).body(new ApiErrors(status.value(), mensagemErro));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException (MethodArgumentNotValidException ex) {
    	String mensagemErro = ex.getAllErrors().stream().map(x -> x.getDefaultMessage()).reduce("", (a, b) -> !a.isEmpty() ? a + ", " + b : b);
    	return new ApiErrors(HttpStatus.BAD_REQUEST.value(), mensagemErro);
    }
}

