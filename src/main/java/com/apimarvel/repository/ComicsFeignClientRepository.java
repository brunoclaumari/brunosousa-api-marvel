package com.apimarvel.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
//@FeignClient(name = "get", url = "http://gateway.marvel.com" .marvel.com)
@FeignClient(url = "http://gateway.marvel.com", name="developer.marvel.com")
public interface ComicsFeignClientRepository {	
	

	@GetMapping(value="/v1/public/comics/{comicId}")
	//@GetMapping(value="/{comicId}")
	public ResponseEntity<String> getComicPorId(
			@PathVariable Long comicId,
			@RequestParam("ts") String timestamp,
			@RequestParam("apikey") String publicKey,
			@RequestParam("hash") String hash
			);
	
	
}
