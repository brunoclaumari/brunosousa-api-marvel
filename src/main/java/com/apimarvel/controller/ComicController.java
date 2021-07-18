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
	
//	@GetMapping(value="/{comicId}/usuario/{usuarioId}")
//	public ResponseEntity<Results> retornoJson(@PathVariable Long comicId,@PathVariable Long usuarioId) 
//	{			
////		JsonElement respostaReq = com.google.gson.JsonParser.parseString(clientFeign.getComicPorId(comicId, timestamp.toString(),publicKey, retornaHash()).getBody());
////		ComicDTO comicDto = comicService.copiaJsonParaComicDto(respostaReq);
//		//		JsonObject jsonobj = respostaReq.getAsJsonObject()
////				.get("data").getAsJsonObject()
////				.getAsJsonArray("results").get(0).getAsJsonObject();
////
////        System.out.println(jsonobj.get("id"));
////        System.out.println(jsonobj.get("title"));
////        System.out.println(jsonobj.getAsJsonArray("prices").get(0).getAsJsonObject().get("price"));
//////        System.out.println(jsonobj.get("creators").getAsJsonObject()
//////        		.getAsJsonArray("items").size()
//////        		);
////        ArrayList<String> teste = new ArrayList<>();
////        JsonArray jsonArrayHa=jsonobj.get("creators").getAsJsonObject().getAsJsonArray("items");
////       		
////        comicService.iteraArrayJson(teste, jsonArrayHa, "name");
////        System.out.println(jsonobj.get("isbn"));
////        System.out.println(jsonobj.get("description"));
////        
//////		Results retorno = data.getResults();
//////		System.out.println(retorno);
//		return null;
//	}
	

	
	
	
	
	

}
