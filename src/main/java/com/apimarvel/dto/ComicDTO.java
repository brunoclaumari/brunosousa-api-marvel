package com.apimarvel.dto;

import java.io.Serializable;

import org.springframework.lang.Nullable;

import com.apimarvel.entities.Comic;
import com.apimarvel.enums.EnumDiaDaSemana;

public class ComicDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private Double price;	
	private String creators;
	private String isbn;
	@Nullable
	private String description;
	private EnumDiaDaSemana discountDay;
	private Boolean activeDiscount = false;

	public ComicDTO() {
	}

	public ComicDTO(Long id, String title, Double price, String creators, String isbn,
			String description, EnumDiaDaSemana discountDay) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.creators = creators;
		this.isbn = isbn;
		this.description = description;
		this.discountDay = discountDay;
	}

	public ComicDTO(Comic entidade) {
		this.id = entidade.getId();
		this.title = entidade.getTitle();
		this.price = entidade.getPrice();
		this.creators = entidade.getCreators();
//		if (entidade.getCreators() != null)
//			copiaAutorParaAutorDto(entidade.getCreators());
		
		this.isbn = entidade.getIsbn();
		this.description = entidade.getDescription();
		this.discountDay = entidade.getDiscountDay();
	}

//	void copiaAutorParaAutorDto(ArrayList<Autor> creators) {
//		creators.forEach(a -> this.creators.add(new AutorDTO(a.getName())));
//	}

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

	public EnumDiaDaSemana getDiscountDay() {
		return discountDay;
	}

	public void setDiscountDay(EnumDiaDaSemana discountDay) {
		this.discountDay = discountDay;
	}	

	public Boolean getActiveDiscount() {
		return activeDiscount;
	}

	public void setActiveDiscount(Boolean activeDiscount) {
		this.activeDiscount = activeDiscount;
	}

	@Override
	public String toString() {
		return "ComicDTO [id=" + id + ", title=" + title + ", price=" + price + ", creators=" + creators + ", isbn="
				+ isbn + ", description=" + description + ", discountDay=" + discountDay.name() + ", activeDiscount="
				+ activeDiscount + "]";
	}


	

}
