package com.example;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.example.utils.ListasUtil;

public class Main {
	static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
	static List<String> tipos = ListasUtil.popularTipos;
	static List<String> tiposSemFiltro = ListasUtil.popularTiposSemFiltro;
	static Document doc;
	static List<String> elementos = new ArrayList<>();
	static HashMap<String, Linha> linhasMap = new HashMap<>();
	static HashMap<String, EixoValor> eixosMap = new HashMap<>();
	static List<Linha> linhasList = new ArrayList<>();
	static Linha linha = new Linha();
	static List<String> titulos = getTitulos();
	static int indiceTitulo = -1;
	static int tipoIndex = 0;
	static int eixo = 1;
	static int indiceEixo = 0;
	static Linha linhaAtual = new Linha();
	static boolean coeficienteisDeslocamento = false;
	static List<String> listaEixos = ListasUtil.popularEixos;
	static List<JSONObject> listaJSON = new ArrayList<>();

	static List<Linha> linhasAtuais = new ArrayList<>();

	// REMOVA !!!
	static HashMap<String, Integer> valores = new LinkedHashMap<>();
	static List<String> chaves = new ArrayList<>();

	public static void main(String[] args) {
		init();

	}

	private static void init() {
		conectar(); // conecta ao sit
		getValores(); // adquire os valores do site

		escreverArquivoJSON(); // escreve o arquivo em si
		for (String key : chaves) {

		}

	}

	private static boolean escreverArquivoJSON() {
		JSONArray arquivoPronto = new JSONArray();
		JSONObject titulosJSON = new JSONObject();

		for (Titulo titulo : gerarJSON()) {// lista de Titulos

			JSONObject tiposJSON = new JSONObject();

			JSONObject eixosJSON = new JSONObject();
			System.out.println("\tTitulo: " + titulo.getNome());

			for (Tipo tipo : titulo.getTipos()) {
				System.out.println("\t\tTipo: " + tipo.getNome());

				JSONObject eixoObj = new JSONObject();
				for (Eixos eixos : tipo.getEixos()) {

					for (EixoValor eixovalor : eixos.getEixos()) {
						System.out.println("\t\t\teixos" + eixovalor.getNumEixo() + ":");
						int eixo = Integer.parseInt(eixovalor.getNumEixo());
						if (eixovalor.getCargaDescarga() == null) {
							System.out.println(eixovalor.getNumEixo() + " ERRO");
						}

						// System.out.println("\t\t\t\tCarga_Descarga: " +
						// eixovalor.getCargaDescarga());
						// System.out.println("\t\t\t\tDeslocamento: " + eixovalor.getDeslocamento());
						EixoValor valor = new EixoValor();
						JSONObject eixoValorJSON = new JSONObject();

						eixoValorJSON.put("Carga_Descarga", eixovalor.getCargaDescarga());
						eixoValorJSON.put("Deslocamento", eixovalor.getDeslocamento());

						eixoObj.put("eixo" + eixo, eixoValorJSON);

					}

				}
				tiposJSON.put(tipo.getNome(), eixoObj);
			}

			titulosJSON.put(titulo.getNome(), tiposJSON);
		}
		arquivoPronto.add(titulosJSON);
		// arquivoPronto.add(titulosJSON);

		try (FileWriter file = new FileWriter("tabelafrete.json")) {
			file.write(arquivoPronto.toJSONString());
			file.flush();
			System.out.println("Arquivo Gerado");
			return true;
		} catch (IOException e) {
			System.out.println("Erro ao gerar arquivo");
			e.printStackTrace();
			return false;
		}

	}

	private static boolean OLDescreverArquivoJSON() {
		List<Titulo> documento = gerarJSON();

		JSONArray jsontiposArray = new JSONArray();
		JSONArray jsonarquivo = new JSONArray();

		for (Titulo titulo : documento) {
			JSONObject jsontitulo = new JSONObject();
			for (Tipo tipo : titulo.getTipos()) {
				System.out.println("{ Titulo:" + titulo.getNome() + "}:");
				JSONObject jsontipos = new JSONObject();

				JSONObject jsoneixo = new JSONObject();
				for (Eixos eixos : tipo.getEixos()) {

					System.out.println("[ Tipo:" + tipo.getNome() + "]");
					for (EixoValor eixoValor : eixos.getEixos()) { // objeto eixos2 ao exiso9 com oq

						JSONObject valorescargaDeslocamento = new JSONObject();

						valorescargaDeslocamento.put(
								"carga_descarga",
								eixoValor.getCargaDescarga());

						valorescargaDeslocamento.put("custo_km", eixoValor.getDeslocamento());

						System.out.println("(carga_descarga: " + eixoValor.getCargaDescarga());
						System.out.println("custo_km: " + eixoValor.getDeslocamento() + ")");
						jsoneixo.put("eixos" + eixoValor.getNumEixo(), valorescargaDeslocamento);
					}
					// contar(eixoValor.getDeslocamento());
					// contar(eixoValor.getCargaDescarga());
					// // eixosN : "carga_descarga" : 2.343, "custo_km" : 12.5

					jsontipos.put(tipo.getNome(), jsoneixo);

				}

				jsontitulo.put(titulo.getNome(), jsontipos);

			}
			jsonarquivo.add(jsontitulo);
		}

		try (FileWriter file = new FileWriter("tabelafrete.json")) {
			file.write(jsonarquivo.toJSONString());
			file.flush();
			System.out.println("Arquivo Gerado");
			return true;
		} catch (IOException e) {
			System.out.println("Erro ao gerar arquivo");
			e.printStackTrace();
			return false;
		}

	}

	private static void contar(String var) {

		if (valores.containsKey(var)) {
			valores.put(var, valores.get(var) + 1);
		} else {
			chaves.add(var);
			valores.put(var, 1);
		}
	}

	private static List<Titulo> gerarJSON() {
		classificaCampo();
		eixo = 1;

		List<Titulo> tabela = new ArrayList<>();
		for (String tituloStr : titulos) {

			Titulo titulo = new Titulo();

			for (String nomeTipo : tipos) {
				Tipo tipo = new Tipo();

				linha = linhasMap.get(tituloStr + nomeTipo);

				Eixos listaDeEixos = new Eixos();
				for (String eixoAtual : listaEixos) {

					EixoValor valor = new EixoValor();

					listaDeEixos.setNome("eixos" + eixoAtual);
					valor.setNumEixo(eixoAtual);

					valor.setCargaDescarga(linha.getEixos_deslocamento().get(eixoAtual));
					valor.setDeslocamento(linha.getEixos_carga_descarga().get(eixoAtual));

					// contar(linha.getEixos_deslocamento().get(eixoAtual));
					// contar(linha.getEixos_carga_descarga().get(eixoAtual));

					if (valor == null) {
						System.out.println("Erro ao adicionar na lista" + eixoAtual);
					}
					listaDeEixos.add(valor);

				}

				tipo.setNome(nomeTipo);
				tipo.addEixo(listaDeEixos);
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

			for (String string : ListasUtil.replaceTipos) {
				texto = texto.replace(string, "");
			}

			if (!tipos.contains(texto) && texto.length() > 1) {
				tipos.add(texto);
			}

		}

	}

	public static void getValores() {

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

			for (String regex : ListasUtil.regexs) {
				if (elemento.matches(regex)) {
					adicionarValorEmLinha(elemento);
				}
			}

			if (elemento.contains("TABELA")) {
				// é o titulo de uma tabela
				System.out.println("\n ------------------- \n\n");
				indiceTitulo = indiceTitulo + 1;

			}

			if (isTipo(elemento)) {
				if (isTipo(elementos.get(i))) {
					// entra aqui caso o proximo elemento tambem seja um tipo
					// serve para resolver o problema de ter dois tipos em seguida
					elemento = "VAZIO";

				} else {
					if (indiceTitulo <= 3) {

						adicionarLinhaEmTabela(linhaAtual, tipos.get(tipoIndex), titulos.get(indiceTitulo));
					}

					if (tipoIndex + 1 == 12) {
						tipoIndex = 0;
					} else {
						tipoIndex = tipoIndex + 1;

					}

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

	private static void adicionarLinhaEmTabela(Linha linhasAtual, String tipo, String titulo) {

		// linhasAtuais.add(linhaAtual);

		linhasMap.put(titulo + tipo, linhaAtual);

	}

	private static boolean isTipo(String elemento) {

		boolean retorno = false;

		for (String tipo : tiposSemFiltro) {
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
