package com.volkovt.administrativo.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;


public class RegraNegocioExcecao extends RuntimeException {
	private static final long serialVersionUID = 202111032110L;

	@Getter
	private HttpStatus status;
	
	public RegraNegocioExcecao(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
	
	public RegraNegocioExcecao(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
