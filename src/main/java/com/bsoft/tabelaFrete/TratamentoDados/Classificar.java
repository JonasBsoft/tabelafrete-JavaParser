package com.bsoft.tabelaFrete.TratamentoDados;

import java.util.ArrayList;
import java.util.List;
import com.bsoft.tabelaFrete.utils.ListasUtil;

/**
 * Classe responsavel por classificar os dados da tabela de preços
 */
public class Classificar {

	static List<String> valores = new ArrayList<>();

	/**
	 * separa os valores adquirido em um array de strings
	 */
	public static void classificarCampos() {

		List<String> elementosValores = PegarValores.elementos;

		int i = 1;
		for (String elemento : elementosValores) {
			if (elemento.length() == 0) {
				if (elementosValores.get(i).length() == 0) {

					if (elementosValores.get(i + 1).length() == 0) {
						elemento = elementosValores.get(i + 2);

					} else {
						elemento = elementosValores.get(i + 1);

					}
				} else {
					elemento = elementosValores.get(i);

				}
			}

			for (String regex : ListasUtil.regExFiltrarValores) {
				if (elemento.matches(regex)) {
					elemento = elemento.replace(",", ".");
					valores.add(elemento);

				}
			}
			i++;
		}
	}
}
