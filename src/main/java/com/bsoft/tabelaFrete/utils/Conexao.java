package com.bsoft.tabelaFrete.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Conexao {
	// * Realiza a conexão com o site */
	private static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
	static Document doc;

	public static Document conectar() {
		try {
			return Jsoup.connect(Conexao.url).get();

		} catch (Exception e) {

		}
		return doc;
	}
}
