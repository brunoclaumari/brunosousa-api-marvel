package com.apimarvel.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.apimarvel.entities.Comic;
import com.apimarvel.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = "Campo nome é obrigatório")
	private String nome;	
	
	@NotBlank(message = "Campo email é obrigatório")
	@Email(message = "Por favor, digite um email válido!!")
	private String email;	
	
	@NotBlank(message = "Campo CPF é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	//@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern="dd/MM/yyyy")
	@NotNull(message = "Campo obrigatório" )
	@PastOrPresent(message = "A data não pode ser futura")
	private Calendar dataNascimento;
	
	private List<ComicDTO> comics = new ArrayList<>();

	public UsuarioDTO() {		
	}

	public UsuarioDTO(Long id, String nome, String email,String cpf, Calendar dataNascimento) {		
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}
	
	public UsuarioDTO(Usuario usu) {		
		this.id = usu.getId();
		this.nome = usu.getNome();
		this.email = usu.getEmail();
		this.cpf = usu.getCpf();
		this.dataNascimento = usu.getDataNascimento();
	}
	
	public UsuarioDTO(Usuario usu,Set<Comic> comics) {
		this(usu);
		comics.forEach(comic -> this.comics.add(new ComicDTO(comic)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<ComicDTO> getComics() {
		return comics;
	}

	public void setComics(List<ComicDTO> comics) {
		this.comics = comics;
	}
	
		
	
}
