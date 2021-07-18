package com.apimarvel.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apimarvel.dto.ComicDTO;
import com.apimarvel.dto.UsuarioDTO;
import com.apimarvel.enums.EnumOperacao;
import com.apimarvel.services.ComicService;
import com.apimarvel.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuService;
	
	@Autowired
	private ComicService comService;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> buscaUsuarioPorId(@PathVariable Long id){
		UsuarioDTO dto = usuService.buscaUsuarioPorId(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> insert(@Valid @RequestBody UsuarioDTO dto) {
		usuService.verificaUsuarioEhValido(dto, EnumOperacao.INSERT, dto.getId());
		for (ComicDTO comic : dto.getComics()) {
			comic = comService.buscaComicPorId(comic.getId());
			comic = comService.insert(comic);					
		}		
		
		dto = usuService.inserir(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto){
		usuService.verificaUsuarioEhValido(dto, EnumOperacao.UPDATE, id);
		for (ComicDTO comic : dto.getComics()) {
			comic = comService.buscaComicPorId(comic.getId());
			comic = comService.insert(comic);					
		}	
		
		dto = usuService.alterar(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}

}
