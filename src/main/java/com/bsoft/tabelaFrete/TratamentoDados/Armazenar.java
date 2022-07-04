package com.bsoft.tabelaFrete.TratamentoDados;

import java.util.ArrayList;
import java.util.List;
import com.bsoft.tabelaFrete.models.Conteudo;
import com.bsoft.tabelaFrete.models.Eixo;
import com.bsoft.tabelaFrete.models.Tipo;
import com.bsoft.tabelaFrete.utils.ListasUtil;

/**
 * Classe responsável por armazenar os dados em listas de conteúdo.
 **/
public class Armazenar {

	public static List<Conteudo> informacoes;

	/**
	 * armazena os dados classificados em Listas de conteudos
	 * que serão utilizado para gerar o arquivo JSON
	 **/
	public static void armazenarConteudo() {

		List<String> valoresClassificados = Classificar.valores;
		List<Conteudo> conteudos = new ArrayList<>();
		int flag = 0;

		for (String titulo : ListasUtil.titulos) {
			/**
			 * instancia uma variavel conteudo e seta o titulo como titulo da
			 **/

			Conteudo conteudo = new Conteudo();
			conteudo.setTitulo(titulo);

			for (String tipos : ListasUtil.popularTipos) {
				Tipo tipo = new Tipo();
				tipo.setNome(tipos);

				for (String eixos : ListasUtil.popularEixos) {
					Eixo eixo = new Eixo();
					eixo.setNome(eixos);
					eixo.setDeslocamento(Double.parseDouble(valoresClassificados.get(flag)));
					eixo.setCarga(Double.parseDouble(valoresClassificados.get(flag + 7)));
					tipo.add(eixo);
					flag++;

				}
				flag = flag + 7;

				conteudo.add(tipo);

			}
			conteudos.add(conteudo);

		}
		informacoes = conteudos;

	}
}
