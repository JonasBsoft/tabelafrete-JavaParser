package com.example.tratamentoDados;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.utils.Conexao;

public class PegarTitulos {

	static Document doc = Conexao.conectar();
	static List<String> titulos = new ArrayList<>();

    public static void getTitulos() {
		/**
		 * atualiza os titulos da lista estatica de titulos
		 */
		Elements elementosTag = doc.getElementsContainingText("TABELA");

		int i = 0;
		titulos = new ArrayList<>();
		for (Element el : elementosTag)
			if (el.text().contains("TABELA")) {
				if (i >= 25) {
					titulos.add(el.text());
				}
				i++;
			}
	}
}
