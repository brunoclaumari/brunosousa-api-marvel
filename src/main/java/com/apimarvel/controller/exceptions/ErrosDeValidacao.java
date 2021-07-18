package com.apimarvel.controller.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ErrosDeValidacao extends ErrosGenericos {	
	private static final long serialVersionUID = 1L;
	
	private List<MensagemPorCampo> errors = new ArrayList<>();

	public List<MensagemPorCampo> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String message) {
		errors.add(new MensagemPorCampo(fieldName, message));
	}

}
