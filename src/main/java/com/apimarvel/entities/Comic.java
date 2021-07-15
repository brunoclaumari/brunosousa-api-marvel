package com.apimarvel.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.apimarvel.enums.EnumDiaDaSemana;
import com.apimarvel.utils.ComicUtils;

@Entity
@Table(name="tb_comic")
public class Comic implements Serializable {
	
	private static final long serialVersionUID = 1L; 
	@Id
	private Long id;
	private String title;
	private Double price;
	private String creators;
	private String isbn;
	
	@Column(columnDefinition = "TEXT")//o columnDefinition como 'TEXT' aceita strings grandes no banco.
	private String description;
	private Integer discountDay;
	
	public Comic() {		
	}

	public Comic(Long id, String title, Double price, String creators, String isbn, String description) {		
		this.id = id;
		this.title = title;
		this.price = price;
		this.creators = creators;
		this.isbn = isbn;
		this.description = description;
		this.discountDay = ComicUtils.retornaDiaDoDesconto(this.isbn);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCreators() {
		return creators;
	}

	public void setCreators(String creators) {
		this.creators = creators;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDiscountDay() {
		return discountDay;
	}

	public void setDiscountDay(Integer discountDay) {
		this.discountDay = discountDay;
	}
	
	public void setDiscountDay() {
		this.discountDay = ComicUtils.retornaDiaDoDesconto(getIsbn());
	} 
	
//	private int retornaDiaDoDesconto(String isbn) {
//		int numeroIsbn = 0;
//		if(!isbn.isEmpty() || !(isbn == null)) {
//			numeroIsbn = (int)isbn.charAt(isbn.length()-1);
//			if(numeroIsbn==0||numeroIsbn==1)
//				return EnumDiaDaSemana.SEGUNDA.getDiaDaSemana();
//			else if(numeroIsbn==2||numeroIsbn==3)
//				return EnumDiaDaSemana.TERCA.getDiaDaSemana();
//			else if(numeroIsbn==4||numeroIsbn==5)
//				return EnumDiaDaSemana.QUARTA.getDiaDaSemana();
//			else if(numeroIsbn==6||numeroIsbn==7)
//				return EnumDiaDaSemana.QUINTA.getDiaDaSemana();
//			else if(numeroIsbn==8||numeroIsbn==9)
//				return EnumDiaDaSemana.SEXTA.getDiaDaSemana();
//		}
//		return numeroIsbn;
//	}
	
}
