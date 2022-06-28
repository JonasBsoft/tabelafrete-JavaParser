package com.example.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Conexao {

    static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";

    public static Document conectar() {
		try {

			return Jsoup.connect(url).get();
		} catch (Exception e) {

		}
		return null;
	}
    
}
