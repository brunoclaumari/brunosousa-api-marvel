package com.apimarvel.controller;

import java.net.URI;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apimarvel.dto.ComicDTO;
import com.apimarvel.services.ComicService;

@RestController
@RequestMapping(value = "/marvelcomics")
public class ComicController {
	
	@Autowired
	private ComicService comicService;	

	
	Long timestamp=Calendar.getInstance().getTimeInMillis();	
	
	@PostMapping(value="/{comicId}")
	public ResponseEntity<ComicDTO> insereComicPorId(@PathVariable Long comicId) 
	{		
		ComicDTO dto = comicService.buscaComicPorId(comicId);
		dto = comicService.insert(dto);
		//comicService.configuraRegrasParaExibicao(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{comicId}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

}
