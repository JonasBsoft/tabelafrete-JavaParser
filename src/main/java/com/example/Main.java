package com.example;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	/**
	 * Método principal
	 * @param args
	 */
	public static void main (String[] args){
		
		String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
		
		try {

			Document doc = Jsoup.connect(url).get();
			
			Elements elementosTag = doc.select("td");
			
			System.out.println("Elementos com a tag <b>:");
			
			for(Element el: elementosTag)
				System.out.println(el.text());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}