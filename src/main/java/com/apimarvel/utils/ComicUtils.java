package com.apimarvel.utils;

import com.apimarvel.enums.EnumDiaDaSemana;

public class ComicUtils {

	public static EnumDiaDaSemana retornaDiaDoDesconto(String isbn) {
		int numeroIsbn = 0;

		if (!isbn.isEmpty() && !(isbn == null)) {
			String ultimo = Character.toString(isbn.charAt(isbn.length() - 1));
			boolean ehInteiro = ultimo.matches("-?\\d+");
			if (ehInteiro) {
				numeroIsbn = Integer.parseInt(ultimo);
				if (numeroIsbn == 0 || numeroIsbn == 1)
					return EnumDiaDaSemana.SEGUNDA;
				else if (numeroIsbn == 2 || numeroIsbn == 3)
					return EnumDiaDaSemana.TERCA;
				else if (numeroIsbn == 4 || numeroIsbn == 5)
					return EnumDiaDaSemana.QUARTA;
				else if (numeroIsbn == 6 || numeroIsbn == 7)
					return EnumDiaDaSemana.QUINTA;
				else if (numeroIsbn == 8 || numeroIsbn == 9)
					return EnumDiaDaSemana.SEXTA;
			}
		}
		return EnumDiaDaSemana.NENHUM;
	}

}
