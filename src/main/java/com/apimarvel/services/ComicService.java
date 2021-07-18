package com.apimarvel.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apimarvel.dto.ComicDTO;
import com.apimarvel.entities.Comic;
import com.apimarvel.repository.ComicRepository;
import com.apimarvel.repository.ComicsFeignClientRepository;
import com.apimarvel.services.exceptions.EntidadeNaoEncontradaException;
import com.apimarvel.utils.ComicUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import feign.FeignException;

@Service
public class ComicService {

	@Autowired
	private ComicsFeignClientRepository comicsFeignClientRepository;

	@Value("${marvel.private_key}")
	private String privateKey;

	@Value("${marvel.public_key}")
	private String publicKey;

	Long timestamp = Calendar.getInstance().getTimeInMillis();

	@Autowired
	ComicRepository repositorio;

	@Transactional(readOnly = true)
	public ComicDTO buscaComicPorId(Long comicId) {
		try {
			JsonElement respostaReq = com.google.gson.JsonParser.parseString(comicsFeignClientRepository
					.getComicPorId(comicId, timestamp.toString(), publicKey, retornaHash()).getBody());

			ComicDTO comicDto = copiaJsonParaComicDto(respostaReq);

			return comicDto;

		} catch (FeignException.NotFound e) {
			throw new EntidadeNaoEncontradaException("O Usuário com id "+comicId+" não foi encontrado na API da Marvel");
		}	
	}

	/**
	 * Adiciona a Comic no banco.
	 */
	@Transactional
	public ComicDTO insert(ComicDTO dto) {
		Comic comic = new Comic();
		copiarDtoParaEntidade(dto, comic);
		comic = repositorio.save(comic);
		ComicDTO newDto = new ComicDTO(comic);
		configuraRegrasParaExibicao(newDto);

		return newDto;
		// return new ComicDTO(comic);
	}

	public String retornaHash() {

		String prepHash = timestamp.toString() + privateKey + publicKey;
		String hash = "";
		MessageDigest decode;
		try {
			decode = MessageDigest.getInstance("MD5");
			decode.update(prepHash.getBytes(), 0, prepHash.length());
			BigInteger big = new BigInteger(1, decode.digest());

			hash = big.toString(16);
			System.out.println(hash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hash;
	}

	public String iteraArrayJson(JsonArray jsonArray, String campo) {
		String autores = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement objJson = jsonArray.get(i).getAsJsonObject().get(campo);

			String valorDoCampo = objJson.getAsString();
			if (i == 0)
				autores += valorDoCampo.trim();
			else
				autores += ", " + valorDoCampo.trim();
		}
		return autores;
	}

	public ComicDTO copiaJsonParaComicDto(JsonElement respostaRequisicao) {
		ComicDTO dto = new ComicDTO();
		JsonObject jsonobj = respostaRequisicao.getAsJsonObject().get("data").getAsJsonObject()
				.getAsJsonArray("results").get(0).getAsJsonObject();

		dto.setId(jsonobj.get("id").getAsLong());
		dto.setTitle(jsonobj.get("title").getAsString());
		dto.setPrice(jsonobj.getAsJsonArray("prices").get(0).getAsJsonObject().get("price").getAsDouble());

		// pega o atributo 'name', do array 'items' do objeto creators, que são os
		// autores
		JsonArray jsonArrayAutores = jsonobj.get("creators").getAsJsonObject().getAsJsonArray("items");
		String autores = iteraArrayJson(jsonArrayAutores, "name");

		dto.setCreators(autores);
		dto.setIsbn(jsonobj.get("isbn").getAsString());
		dto.setDescription(jsonobj.get("description").isJsonNull() ? "" : jsonobj.get("description").getAsString());
		dto.setDiscountDay(ComicUtils.retornaDiaDoDesconto(dto.getIsbn()));

		return dto;
	}

	private void copiarDtoParaEntidade(ComicDTO dto, Comic entidade) {
		entidade.setId(dto.getId());
		entidade.setCreators(dto.getCreators());
		entidade.setDescription(dto.getDescription());
		entidade.setIsbn(dto.getIsbn());
		entidade.setDiscountDay(dto.getDiscountDay());
		entidade.setPrice(dto.getPrice());
		entidade.setTitle(dto.getTitle());

	}


	public void verificaDescontoAtivo(ComicDTO dto) {
		int diaSemana = LocalDate.now().getDayOfWeek().getValue();
		if (dto.getDiscountDay().getDiaDaSemana() == diaSemana) {
			dto.setActiveDiscount(true);
			System.out.println("Desconto foi ativado");
		}
	}

	public void aplicaPrecoComDesconto(ComicDTO dto) {
		Double preco = dto.getPrice();
		if (dto.getActiveDiscount() == true) {
			NumberFormat formatter = new DecimalFormat("#0.00");
			Double novoPreco = Double.parseDouble(formatter.format(preco * 0.9).replace(',', '.'));
			dto.setPrice(novoPreco);
		}
	}

	public void configuraRegrasParaExibicao(ComicDTO dto) {
		verificaDescontoAtivo(dto);
		aplicaPrecoComDesconto(dto);
	}

//	//Os autores serão armazenados no banco como string separados por '-' e
//	// esse método abaixo vai transformar a string em ArrayList do objeto Autor
//	public ArrayList<AutorDTO> trataStringAutoresParaListaDeAutores(String stringAutores){
//		String[] vetor = stringAutores.split("-");
//		ArrayList<AutorDTO> autores=new ArrayList<>();
//		for (String nome : vetor) {
//			autores.add(new AutorDTO(nome));
//		}
//		return autores;		
//	}
}
