package com.apimarvel.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apimarvel.controller.exceptions.ErrosDeValidacao;
import com.apimarvel.dto.UsuarioDTO;
import com.apimarvel.entities.Usuario;
import com.apimarvel.enums.EnumOperacao;
import com.apimarvel.repository.ComicRepository;
import com.apimarvel.repository.ComicsFeignClientRepository;
import com.apimarvel.repository.UsuarioRepository;
import com.apimarvel.services.exceptions.EntidadeNaoEncontradaException;
import com.apimarvel.services.exceptions.UsuarioInvalidoException;

@Service
public class UsuarioService {

	// autowired faz a injeção de dependencia
	@Autowired
	private UsuarioRepository repositorioUsuario;

	@Autowired
	private ComicRepository repositorioComic;

	@Autowired
	ComicsFeignClientRepository feignComic;

	@Transactional(readOnly = true)
	public UsuarioDTO buscaUsuarioPorId(Long id) {
		/*
		 * No lugar de um try/catch é usado o método 'orElseThrow' que lança a exceção
		 * personalizada criada caso o 'usu' não traga valores na requisição.
		 */
		Optional<Usuario> op = repositorioUsuario.findById(id);
		Usuario usu = op
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não foi encontrada"));

		return new UsuarioDTO(usu, usu.getComics());
	}

	@Transactional
	public UsuarioDTO inserir(UsuarioDTO dto) {

		Usuario usu = new Usuario();
		copiarDtoParaEntidade(dto, usu);
		usu = repositorioUsuario.save(usu);

		return new UsuarioDTO(usu);
	}
	
	@Transactional
	public UsuarioDTO alterar(Long id, UsuarioDTO dto) {
		try {
			
			Usuario usu = repositorioUsuario.getOne(id);
			copiarDtoParaEntidade(dto, usu);
			usu = repositorioUsuario.save(usu);			
			return new UsuarioDTO(usu);
			
		} catch (EntityNotFoundException e) {
			throw new EntidadeNaoEncontradaException("O Usuário com id " + id + " não foi encontrado");
		}		
	}

	private void copiarDtoParaEntidade(UsuarioDTO dto, Usuario entidade) {

		entidade.setNome(dto.getNome());
		entidade.setCpf(dto.getCpf());
		entidade.setEmail(dto.getEmail());
		entidade.setDataNascimento(dto.getDataNascimento());
		entidade.getComics().clear();

		dto.getComics().forEach(comicDto -> {
			entidade.getComics().add(repositorioComic.getOne(comicDto.getId()));
		});
	}

	public void verificaUsuarioEhValido(UsuarioDTO dto, EnumOperacao operacao, Long idRequest) {
		// boolean valido=false;

		ErrosDeValidacao errosCapturados = new ErrosDeValidacao();
		Usuario usu = new Usuario();
		switch (operacao) {
		case INSERT:
			usu = repositorioUsuario.findByEmail(dto.getEmail());
			if (usu != null)
				errosCapturados.addError("Email", "Esse email já existe!!");
			usu = repositorioUsuario.findByCpf(dto.getCpf());
			if (usu != null)
				errosCapturados.addError("Cpf", "Esse Cpf já existe!!");
			break;
		case UPDATE:
			usu = repositorioUsuario.findByEmail(dto.getEmail());
			if (usu != null && idRequest != usu.getId())
				errosCapturados.addError("Email", "Esse email já existe!!");
			usu = repositorioUsuario.findByCpf(dto.getCpf());
			if (usu != null && idRequest != usu.getId())
				errosCapturados.addError("Cpf", "Esse Cpf já existe!!");
			break;
		}

		if (!errosCapturados.getErrors().isEmpty()) {
			throw new UsuarioInvalidoException(errosCapturados);
		}

	}

}
