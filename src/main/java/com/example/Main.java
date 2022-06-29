package com.example;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.example.utils.LimpaElementos;

public class Main {
	static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
	static List<String> tipos = new ArrayList<>();
	static Document doc;
	static List<String> elementos = new ArrayList<>();
	static List<Linha> linhas = new ArrayList<>();
	static Linha linha = new Linha();
	static List<String> titulos = new ArrayList<>();
	static int indiceTitulo = -1;
	static int eixo = 1;
	static int indiceEixo = 0;
	static Linha linhaAtual = new Linha();
	static boolean coeficienteisDeslocamento = false;
	static List<String> listaEixos = new ArrayList<>();

	public static void main(String[] args) {
		init();

	}

	private static void init() {
		conectar(); // conecta ao site
		popularEixos(); // popula lista de eixos
		popularTipos(); // ||
		getTitulos(); // separa os titulos do arquivo do site
		getValores();
		classificaCampo();
		if (escreverArquivoJSON()) {
			System.out.println("Arquivo Gerado");
		}

	}

	private static boolean escreverArquivoJSON() {
		List<Titulo> documento = gerarJSON();
		JSONObject jsoneixo = new JSONObject();
		JSONObject jsontipos = new JSONObject();
		JSONObject jsontitulo = new JSONObject();
		JSONArray jsonarquivo = new JSONArray();

		for (Titulo titulo : documento) {

			for (Tipo tipo : titulo.getTipos()) {

				for (Eixos eixos : tipo.getEixos()) {

					for (EixoValor eixoValor : eixos.getEixos()) {

						JSONObject valorescargaDeslocamento = new JSONObject();

						valorescargaDeslocamento.put("carga_descarga", eixoValor.getCargaDescarga());
						valorescargaDeslocamento.put("custo_km", eixoValor.getDeslocamento());
						jsoneixo.put("eixos" + eixoValor.getNumEixo(), valorescargaDeslocamento);
					}
				}
				jsontipos.put(tipo.getNome(), jsoneixo);
			}
			jsontitulo.put(titulo.getNome(), jsontipos);
		}
		jsonarquivo.add(jsontitulo);

		try (FileWriter file = new FileWriter("tabelafrete.json")) {
			file.write(jsonarquivo.toJSONString());
			file.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	private static void popularTipos() {
		tipos = new ArrayList<String>();

		tipos.add("granelSolido");
		tipos.add("granelLiquido");
		tipos.add("frigorificada");
		tipos.add("conteinerizada");
		tipos.add("cargaGeral");
		tipos.add("neogranel");
		tipos.add("perigosaGranelSolido");
		tipos.add("perigosaGranelLiquido");
		tipos.add("perigosaCargaFrigorificada");
		tipos.add("perigosaConteinerizada");
		tipos.add("perigosaCargaGeral");
		tipos.add("granelPressurizada");
	}

	private static void popularEixos() {
		listaEixos = new ArrayList<String>();
		listaEixos.add("2");
		listaEixos.add("3");
		listaEixos.add("4");
		listaEixos.add("5");
		listaEixos.add("6");
		listaEixos.add("7");
		listaEixos.add("9");
	}

	private static List<Titulo> gerarJSON() {
		popularTipos();
		eixo = 1;

		List<Titulo> tabela = new ArrayList<>();
		for (String tituloStr : titulos) {

			Titulo titulo = new Titulo();
			for (String nomeTipo : tipos) {
				Tipo tipo = new Tipo();

				for (Linha linha : linhas) {

					HashMap<String, String> deslocamento = linha.getEixos_deslocamento();
					HashMap<String, String> carga_descarga = linha.getEixos_carga_descarga();
					Eixos listaDeEixos = new Eixos();

					for (String eixoAtual : listaEixos) {
						EixoValor valor = new EixoValor();

						listaDeEixos.setNome("eixos" + eixoAtual);
						valor.setNumEixo(eixoAtual);
						valor.setCargaDescarga(carga_descarga.get(eixoAtual));
						valor.setDeslocamento(deslocamento.get(eixoAtual));
						listaDeEixos.add(valor);

					}

					tipo.setNome(nomeTipo);
					tipo.addEixo(listaDeEixos);
				}
				titulo.addTipo(tipo);

			}
			titulo.setNome(tituloStr);
			tabela.add(titulo);
		}

		return tabela;
	}

	public static void conectar() {
		try {

			doc = Jsoup.connect(Main.url).get();
		} catch (Exception e) {

		}

	}

	public static List<String> getTitulos() {
		titulos = new ArrayList<>();
		titulos.add("lotacao");
		titulos.add("somenteAutomotor");
		titulos.add("lotacaoAltoDesempenho");
		titulos.add("somenteAutomotorAltoDesempenho");
		return titulos;

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

			for (String regex : LimpaElementos.regexs) {
				if (elemento.matches(regex)) {
					adicionarValorEmLinha(elemento);
				}
			}

			if (elemento.contains("TABELA")) {
				// é o titulo de uma tabela
				indiceTitulo = indiceTitulo + 1;

			}

			if (isTipo(elemento)) {
				if (isTipo(elementos.get(i))) {
					// entra aqui caso o proximo elemento tambem seja um tipo
					// serve para resolver o problema de ter dois tipos em seguida
					elemento = "VAZIO";

				} else {
					adicionarLinhaEmTabela(linhaAtual);
					linhaAtual = new Linha();

				}

			}
			i++;
		}

	}

	private static void adicionarValorEmLinha(String elemento) {
		String eixo = String.valueOf(getEixo());
		elemento = elemento.replace(",", ".");

		if (coeficienteisDeslocamento) {
			linhaAtual.addEixos_deslocamento(eixo, elemento);

		}
		linhaAtual.addEixos_CargaeDescarga(eixo, elemento);

	}

	private static void adicionarLinhaEmTabela(Linha linhaAtual) {

		linhas.add(linhaAtual);

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

	public static int getNextIndice() {

		if (indiceEixo + 1 == 7) {
			return indiceEixo = 0;

		}
		return indiceEixo + 1;

	}

	public static int getEixo() {

		int retorno = Integer.parseInt(listaEixos.get(indiceEixo));

		indiceEixo = getNextIndice();

		return retorno;

	}
}
