package com.example.tratamentoDados;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.utils.Conexao;
import com.example.utils.LimpaElementos;

public class PegarTipos {

    static Document doc = Conexao.conectar();
    static List<String> tipos = new ArrayList<>();

    public static void getTipos() {
		/**
		 * atualiza os tipos da lista estatica de tipos
		 */

		for (Element e : doc.select("tr")) {
			String text = e.text();

			String texto = "";

			for (int j = 0; j < text.length(); j++) {
				// itera letra por letra de cada linha

				if (text.contains("#") || text.contains("Páginas") || text.contains("Visitantes")) {
					j = text.length(); // remove as linhas Paginas/Visitantes/#Tipo de carga
				} else {

					if (text.charAt(j) == ')') {

						j = text.length() + 20;
					} else {

						texto = texto + text.charAt(j);

					}

				}
			}
			if (texto.length() > 0) {

				if (!(texto.charAt(texto.length() - 1) == '(')) {
					texto = texto + ")";
				}
			}

			texto = texto + "!";
			texto = texto.replaceAll("[0-9]", "");

			for (String string : LimpaElementos.replaceTipos) {
				texto = texto.replace(string, "");
			}

			if (!tipos.contains(texto) && texto.length() > 1) {
				tipos.add(texto);
			}

		}
	}
    
}
