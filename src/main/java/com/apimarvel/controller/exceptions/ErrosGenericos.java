package com.apimarvel.controller.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class ErrosGenericos implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Instant timestamp;
	private Integer status;
	private String erro;
	private String mensagem;
	private String caminhoEndPoint;
	
	public ErrosGenericos() {
		
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getCaminhoEndPoint() {
		return caminhoEndPoint;
	}

	public void setCaminhoEndPoint(String caminhoEndPoint) {
		this.caminhoEndPoint = caminhoEndPoint;
	}
	
}
