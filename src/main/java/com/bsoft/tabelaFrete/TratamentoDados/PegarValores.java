package com.bsoft.tabelaFrete.TratamentoDados;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.bsoft.tabelaFrete.utils.Conexao;
import com.bsoft.tabelaFrete.utils.ListasUtil;


public class PegarValores {
    static List<String> elementos = new ArrayList<>();
    static Document doc = Conexao.conectar();

    public static void adquirirValores() {
		List<String> elementosTabelas = new ArrayList<>();
		try {
			for (Element e : doc.select(".dou-paragraph")) {
				String text = e.text();
				if (limparElementos(text)) {
					elementosTabelas.add(text);
				}
			}
			elementos = elementosTabelas;
		} catch (Exception e) {
		}
    }
    public static boolean limparElementos(String elemento) {
		if (elemento.length() == 1) {
			return false;
		}

		for (String string : ListasUtil.palavrasParaRetirar) {
			if (elemento.contains(string)) {
				return false;
			}
		}
		return true;   
    }
}
