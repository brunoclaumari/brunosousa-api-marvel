package com.apimarvel.entities;

import java.io.Serializable;

public class Autor implements Serializable {
	//Essa classe é apenas para comportar os nomes dos autores
	//porém não será criada nenhuma entidade no banco para ela por enquanto
	private static final long serialVersionUID = 1L; 
	
	private String nome;
	
	public Autor() {		
	}
	
	public Autor(String nome) {		
		this.nome = nome;
	}

	public String getName() {
		return nome;
	}

	public void setName(String nome) {
		this.nome = nome;
	}	

}
