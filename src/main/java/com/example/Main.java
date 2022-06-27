package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
	static List<String> titulos = new ArrayList<>();
	static Document doc = conectar();

	public static void main(String[] args) {
		getTitulos(doc);
		getTitulos(doc);
		getTitulos(doc);
		for (String titulo : titulos) {
			System.out.println(titulo);
		}

	}

	public static Document conectar() {
		try {

			return Jsoup.connect(Main.url).get();
		} catch (Exception e) {

		}
		return null;
	}

	public static void getTitulos(Document doc) {
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