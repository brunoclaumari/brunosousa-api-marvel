package com.apimarvel.enums;

public enum EnumDiaDaSemana {
	SEGUNDA(1),TERCA(2),QUARTA(3),QUINTA(4),SEXTA(5);

	private int diaDaSemana;
	
	EnumDiaDaSemana(int diaDaSemana) {		
		this.setDiaDaSemana(diaDaSemana);
	}

	public int getDiaDaSemana() {
		return diaDaSemana;
	}

	public void setDiaDaSemana(int diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

}
