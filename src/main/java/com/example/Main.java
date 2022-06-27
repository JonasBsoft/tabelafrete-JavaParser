package com.example;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
	static List<String> titulos = new ArrayList<>();
	static List<String> tipos = new ArrayList<>();
	static Document doc = conectar();

	public static void main(String[] args) {
		getTitulos();
		getTipos();

	}

	public static Document conectar() {
		try {

			return Jsoup.connect(Main.url).get();
		} catch (Exception e) {

		}
		return null;
	}

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
			texto = texto.replace("(!", "");
			texto = texto.replace("(CCD)", "");
			texto = texto.replace("(CC)", "");
			texto = texto.replaceAll("[0-9]", "");
			texto = texto.replace("Carga e descarga", "");
			texto = texto.replace("()!", "");
			texto = texto.replace("!", "");
			texto = texto.replace("     )", "");
			texto = texto.replace("  ", "");
			texto = texto.replace("Deslocamento", "");

			if (!tipos.contains(texto) && texto.length() > 1) {
				tipos.add(texto);
			}

		}

	}
}