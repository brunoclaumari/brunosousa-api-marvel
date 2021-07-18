package com.apimarvel.services.exceptions;

import com.apimarvel.controller.exceptions.ErrosDeValidacao;

public class UsuarioInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	ErrosDeValidacao listaErros;
	
	public UsuarioInvalidoException(ErrosDeValidacao errosCapturados) {
		this.listaErros=errosCapturados;
	}
	
	public ErrosDeValidacao getErrosDeValidacao() {
		return listaErros;
	}

}
