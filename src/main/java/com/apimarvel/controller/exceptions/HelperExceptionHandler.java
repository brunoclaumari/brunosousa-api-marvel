package com.apimarvel.controller.exceptions;

import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;

import com.apimarvel.services.exceptions.BancoDeDadosException;
import com.apimarvel.services.exceptions.EntidadeNaoEncontradaException;
import com.apimarvel.services.exceptions.UsuarioInvalidoException;

import feign.Feign;
import feign.FeignException;

@ControllerAdvice
public class HelperExceptionHandler {
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<ErrosGenericos> entityNotFound(EntidadeNaoEncontradaException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.NOT_FOUND;//404
		
		ErrosGenericos err=new ErrosGenericos();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Entidade ou recurso não encontrado");
		err.setMensagem(e.getMessage());
		err.setCaminhoEndPoint(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(BancoDeDadosException.class)
	public ResponseEntity<ErrosGenericos> database(BancoDeDadosException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.BAD_REQUEST;//400
		
		ErrosGenericos err=new ErrosGenericos();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Erro de acesso ao banco");
		err.setMensagem(e.getMessage());
		err.setCaminhoEndPoint(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(UsuarioInvalidoException.class)
	public ResponseEntity<ErrosDeValidacao> validation(UsuarioInvalidoException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.BAD_REQUEST;//400
		
		ErrosDeValidacao err=e.getErrosDeValidacao();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Erro de validação");
		err.setMensagem(e.getMessage());
		err.setCaminhoEndPoint(request.getRequestURI());		

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrosDeValidacao> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.UNPROCESSABLE_ENTITY;//422->Entidade não pôde ser processada
		
		ErrosDeValidacao err=new ErrosDeValidacao();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Erro de validação");
		err.setMensagem(e.getMessage());
		err.setCaminhoEndPoint(request.getRequestURI());
		
		for(FieldError f: e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}		
		
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrosGenericos> illegalArgument(IllegalArgumentException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.BAD_REQUEST;//400
		
		ErrosGenericos err=new ErrosGenericos();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Erro na requisição");
		err.setMensagem(e.getMessage());
		err.setCaminhoEndPoint(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	/*
	 * feign.FeignException$Unauthorized: [401 Unauthorized] during [GET] to [http://gateway.marvel.com/v1/public/comics/1045?ts=1626318047042&apikey=ff2f6afb8314e925c3d590de6d1075c6&hash=86f6902460e2dad6f8bd3bd5bf62515] [ComicsFeignClientRepository#getComicPorId(Long,String,String,String)]: [{"code":"InvalidCredentials","message":"That hash, timestamp and key combination is invalid."}]
	at feign.FeignException.clientErrorStatus(FeignException.java:197) ~[feign-core-10.12.jar:na]
	 * */
	@ExceptionHandler(FeignException.Unauthorized.class)
	public ResponseEntity<ErrosGenericos> feignExceptionNaoAutorizado(FeignException.Unauthorized e, HttpServletRequest request){
		HttpStatus status=HttpStatus.UNAUTHORIZED;//401
		
		ErrosGenericos err=new ErrosGenericos();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Erro de credenciais ou chave de API da Marvel!");
		err.setMensagem("Contactar o desenvolvedor");
		err.setCaminhoEndPoint(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
//	@ExceptionHandler(FeignException.NotFound.class)
//	public ResponseEntity<ErrosGenericos> feignExceptionNaoEncontrado(FeignException.NotFound e, HttpServletRequest request){
//		HttpStatus status=HttpStatus.NOT_FOUND;//404		
//		
//		
//		ErrosGenericos err=new ErrosGenericos();
//		err.setTimestamp(Instant.now());
//		err.setStatus(status.value());
//		err.setErro("Comic não encontrada!!");
//		err.setMensagem(e.getMessage());
//		err.setCaminhoEndPoint(request.getRequestURI());
//		
//		return ResponseEntity.status(status).body(err);
//	}
}
