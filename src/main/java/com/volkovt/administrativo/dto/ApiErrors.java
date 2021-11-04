package com.volkovt.administrativo.dto;

import lombok.Getter;

@Getter
public class ApiErrors {

	Integer status_code;
	String mensagem;

    public ApiErrors(Integer status_code, String mensagem) {
        this.status_code = status_code;
        this.mensagem = mensagem;
    }
}