package com.example.tratamentoDados;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import com.example.Linha;
import com.example.utils.Conexao;
import com.example.utils.ValorEmLinha;

public class classficaCampos {

    static List<String> tipos = new ArrayList<>();
	static Document doc = Conexao.conectar();
	
	static List<Linha> linhas = new ArrayList<>();
	static Linha linha = new Linha();
	static List<String> titulos = new ArrayList<>();
	static int eixo = 1;
	static Linha linhaAtual = new Linha();
	static boolean coeficienteisDeslocamento = false;

    public static void classificaCampo() {

		int i = 1;

		for (String elemento :PegarValores.elementos) {

			if (elemento.length() == 0) {
				if (PegarValores.elementos.get(i).length() == 0) {

					if (PegarValores.elementos.get(i + 1).length() == 0) {
						elemento = PegarValores.elementos.get(i + 2);
					} else {
						elemento = PegarValores.elementos.get(i + 1);
					}
				} else {
					elemento = PegarValores.elementos.get(i);
				}

			}
			if (elemento.contains("R$")) {
				elemento = "UNIDADE: " + elemento;
			}
			if (elemento.contains("(CC")) {
				coeficienteisDeslocamento = !coeficienteisDeslocamento;

				elemento = "COEFICIENTE: " + elemento;
			}

			if (elemento.matches("\\d.\\d\\d\\d\\d")) {

				elemento = ValorEmLinha.adicionarValorEmLinha(elemento);

			}
			if (elemento.matches("\\d\\d.\\d\\d\\d\\d")) {
				elemento = ValorEmLinha.adicionarValorEmLinha(elemento);

			}
			if (elemento.matches("\\d\\d.\\d\\d")) {
				elemento = ValorEmLinha.adicionarValorEmLinha(elemento);

			}
			if (elemento.matches("\\d\\d\\d.\\d\\d")) {
				elemento = ValorEmLinha.adicionarValorEmLinha(elemento);

			}
			if (ValorEmLinha.isTipo(elemento)) {
				if (ValorEmLinha.isTipo(PegarValores.elementos.get(i))) {
					elemento = "VAZIO";
				} else {
					if (linhaAtual.getTipo().equals("")) {
					} else {
						ValorEmLinha.adicionarLinhaEmTabela(linhaAtual);

					}
					linhaAtual = new Linha();
					linhaAtual.setTipo(elemento);
					elemento = "[TIPO] " + elemento;

				}
			}
			System.out.println(elemento);
			i++;
		}

	}
}
