package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.example.utils.LimpaElementos;

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
	static List<String> listaEixos = new ArrayList<>();

	public static void main(String[] args) {

		listaEixos.add("2");
		listaEixos.add("3");
		listaEixos.add("4");
		listaEixos.add("5");
		listaEixos.add("6");
		listaEixos.add("7");
		listaEixos.add("9");
		getTitulos(); // MUITO IMPORTANTE CHAMAR ESSA FUNCAO 1 VEZ POR EXECUCAO
		getTipos();
		getValores();
		classificaCampo();

		List<Titulo> documento = gerarJSON();

		JSONObject jsoneixo = new JSONObject();
		JSONObject jsontipos = new JSONObject();
		JSONObject jsontitulo = new JSONObject();
		JSONArray jsonarquivo = new JSONArray();

		for (Titulo titulo : documento) {


			for (Tipo tipo : titulo.getTipos()) {


				for (Eixos eixos : tipo.getEixos()) {


					for (EixoValor eixoValor : eixos.getEixos()) {

						System.out.println("{" + titulo.getNome() + ":");
						System.out.println(tipo.getNome() + ":");
						System.out.println("eixos" + eixoValor.getNumEixo() + ":");
						// System.out.println("Deslocamento: " + eixoValor.getDeslocamento());
						// System.out.println("Carga Descarga: " + eixoValor.getCargaDescarga());

						JSONObject valorescargaDeslocamento = new JSONObject(); 
						valorescargaDeslocamento.put("custo_km", eixoValor.getDeslocamento());
						valorescargaDeslocamento.put("carga_descarga", eixoValor.getCargaDescarga() );

						
						jsoneixo.put("eixos" + eixoValor.getNumEixo(), valorescargaDeslocamento);

					}
	
				}

				jsontipos.put(tipo.getNome(), jsoneixo);
			}
			jsontitulo.put(titulo.getNome(), jsontipos);
		}

		jsonarquivo.add(jsontitulo);

		try (FileWriter file = new FileWriter("tabelafrete.json")) {
			//We can write any JSONArray or JSONObject instance to the file
			//System.out.println(employeeList);
			file.write(jsonarquivo.toJSONString()); 
			file.flush();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
			// exibirElementos(); Essa é a funcao antiga que usava String para gerar o JSON
	
	
	 


	
	}


	private static List<Titulo> gerarJSON() {

		JSONObject jsonObject = new JSONObject();

		FileWriter writeFile = null;
		List<String> tipos = new ArrayList<>();

		// terminou a linha, vai estar em 9, basta resetar para 2 os eixos
		eixo = 1;

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

	private static void escrever(String arquivo) {
		try {

			FileWriter arq = new FileWriter("C:\\Users\\Bsoft\\Desktop\\arquivo.json");
			PrintWriter gravarArq = new PrintWriter(arq);

			gravarArq.print(arquivo);

			arq.close();
		} catch (Exception e) {

		}
	}

	private static void exibirElementos() {
		int i = 1;
		eixo = 1;

		String saida = "{";
		System.out.println("Arquivo gerado");

		String ultimoTitulo = "";
		for (Linha linha : linhas) {
			linha.setTipo(linha.getTipo().replace(" ", "_"));
			if (ultimoTitulo != linha.getTitulo()) {

				ultimoTitulo = linha.getTitulo();
				saida = saida + "\"" + linha.getTitulo() + "\":{";

			}
			saida = saida + "\"" + linha.getTipo() + "\":{";
			do {
				i = getEixo();

				String eixoStr = String.valueOf(i);

				saida = saida + "\"eixos" + i + "\":{";
				saida = saida + "\"custo_km\":";
				saida = saida + linha.getEixos_deslocamento().get(eixoStr) + ",";
				saida = saida + "\"carga_descarga\":";
				saida = saida + linha.getEixos_carga_descarga().get(eixoStr);
				if (i == 9) {

					saida = saida + "}";
				} else {
					saida = saida + "},";
				}

			} while (i < 9);

			if (linha.getTipo().equals(tipos.get(tipos.size() - 1))) {
				saida = saida + "}";
			} else {
				saida = saida + "},";
			}
			if (linha.getTitulo().equals(titulos.get(titulos.size() - 1))) {
				saida = saida + "}";
			} else {
				saida = saida + "},";
			}
		}

		escrever(saida);
	}

	public static Document conectar() {
		try {

			return Jsoup.connect(Main.url).get();
		} catch (Exception e) {

		}
		return null;
	}

	public static List<String> getTitulos() {
		titulos = new ArrayList<>();
		titulos.add("lotacao");
		titulos.add("somenteAutomotor");
		titulos.add("lotacaoAltoDesempenho");
		titulos.add("somenteAutomotorAltoDesempenho");
		return titulos;

	}

	public static void OLDgetTitulos() {
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
		elemento = elemento.replace(",", ".");

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
