package com.example.tratamentoDados;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import com.example.utils.Conexao;
import com.example.utils.LimpaElementos;

public class PegarValores {

    
	static Document doc = Conexao.conectar();
	static List<String> elementos = new ArrayList<>();

    
    public static void getValores() {

        

		List<String> elementosTabelas = new ArrayList<>();
        

		for (Element e : doc.select(".dou-paragraph")) {

			String text = e.text();

			if (limparElementos(text)) {
				elementosTabelas.add(text);
			}
		}

		elementos = elementosTabelas;

    
	}

	public static boolean limparElementos(String elemento) {

		if (elemento.length() == 1) {
			return false;
		}

		for (String string : LimpaElementos.palavrasParaRetirar) {
			if (elemento.contains(string)) {
				return false;
			}
		}

		return true;


	}
    
}
