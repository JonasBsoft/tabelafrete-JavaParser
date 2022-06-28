package com.example;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
	static List<String> tipos = new ArrayList<>();
	static Document doc = conectar();
	static List<String> elementos = new ArrayList<>();
	static List<Linha> linhas = new ArrayList<>();
	static Linha linha = new Linha();
	static List<String> titulos = new ArrayList<>();
	static int indiceTitulo = -1;
	static int eixo = 1;
	static Linha linhaAtual = new Linha();
	static boolean coeficienteisDeslocamento = false;

	public static void main(String[] args) {
		getTitulos();
		getTipos();
		getValores();
		classificaCampo();

		exibirElementos();

	}

	private static void exibirElementos() {
		int i = 1;
		System.out.println("Entrando no exibir");
		for (Linha linha : linhas) {
			System.out.println("{");
			System.out.println(linha.getTitulo() + ":{");
			System.out.println(linha.getTipo() + ":{");
			System.out.println(linha.pegarValores(linha.getEixos_deslocamento()));
			System.out.println("}");
		}
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
		Elements elementosTag = Main.doc.getElementsContainingText("TABELA");

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
			texto = texto.replaceAll("[0-9]", "");

			for (String string : LimpaElementos.replaceTipos) {
				texto = texto.replace(string, "");
			}

			if (!tipos.contains(texto) && texto.length() > 1) {
				tipos.add(texto);
			}

		}

	}

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

	public static void classificaCampo() {

		int i = 1;

		for (String elemento : elementos) {

			if (elemento.length() == 0) {
				if (elementos.get(i).length() == 0) {

					if (elementos.get(i + 1).length() == 0) {
						elemento = elementos.get(i + 2);
					} else {
						elemento = elementos.get(i + 1);
					}
				} else {
					elemento = elementos.get(i);
				}

			}

			if (elemento.contains("(CC")) {
				coeficienteisDeslocamento = !coeficienteisDeslocamento;

			}

			if (elemento.matches("\\d.\\d\\d\\d\\d")) {

				elemento = adicionarValorEmLinha(elemento);

			}
			if (elemento.matches("\\d\\d.\\d\\d\\d\\d")) {
				elemento = adicionarValorEmLinha(elemento);

			}
			if (elemento.matches("\\d\\d.\\d\\d")) {
				elemento = adicionarValorEmLinha(elemento);

			}
			if (elemento.matches("\\d\\d\\d.\\d\\d")) {
				elemento = adicionarValorEmLinha(elemento);

			}
			if (elemento.contains("TABELA")) {
				// é o titulo de uma tabela

				indiceTitulo = indiceTitulo + 1;

			}

			if (isTipo(elemento)) {
				linhaAtual.setTipo(elemento);
				linhaAtual.setTitulo(titulos.get(indiceTitulo));
				if (isTipo(elementos.get(i))) {
					// entra aqui caso o proximo elemento tambem seja um tipo
					// serve para resolver o problema de ter dois tipos em seguida
					elemento = "VAZIO";

				} else {
					adicionarLinhaEmTabela(linhaAtual);
					linhaAtual = new Linha();

				}

				// System.out.println(linhaAtual.getTitulo());

			}
			i++;
		}

	}

	private static String adicionarValorEmLinha(String elemento) {
		String eixo = String.valueOf(getEixo());

		if (coeficienteisDeslocamento) {
			linhaAtual.addEixos_deslocamento(eixo, elemento);

		}
		linhaAtual.addEixos_CargaeDescarga(eixo, elemento);
		return "[" + eixo + "]VALORES: " + elemento;
	}

	private static void adicionarLinhaEmTabela(Linha linhaAtual) {

		linhas.add(linhaAtual);
		// se nao for null:

	}

	private static boolean isTipo(String elemento) {

		boolean retorno = false;

		for (String tipo : tipos) {
			if (tipo.contains(elemento)) {
				retorno = true;
			}

		}
		return retorno;
	}

	public static int getEixo() {
		if (eixo == 7) {
			eixo = 9;
			return eixo;
		}
		if (eixo == 9) {
			eixo = 2;
			return eixo;
		}
		eixo = eixo + 1;

		return eixo;
	}
}
