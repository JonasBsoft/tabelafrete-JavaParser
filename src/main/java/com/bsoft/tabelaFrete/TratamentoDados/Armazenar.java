package com.bsoft.tabelaFrete.TratamentoDados;

import java.util.ArrayList;
import java.util.List;
import com.bsoft.tabelaFrete.models.Conteudo;
import com.bsoft.tabelaFrete.models.Eixo;
import com.bsoft.tabelaFrete.models.Tipo;
import com.bsoft.tabelaFrete.utils.ListasUtil;
public class Armazenar {

    public static List<Conteudo> informacoes;

    public static void armazenarConteudo() {

        List<String> valoresClassificados = Classificar.valores;        
        
		List<Conteudo> conteudos = new ArrayList<>();
		int flag = 0;
		int flag2 = flag + 7;
		for (String titulo : ListasUtil.titulos) {

			Conteudo conteudo = new Conteudo();
			conteudo.setTitulo(titulo);
			for (String tipos : ListasUtil.popularTipos) {
				Tipo tipo = new Tipo();
				tipo.setNome(tipos);
				for (String eixos : ListasUtil.popularEixos) {
					Eixo eixo = new Eixo();
					eixo.setNomeEixo(eixos);
					eixo.setDeslocamento(Double.parseDouble(valoresClassificados.get(flag)));
					eixo.setCarga(Double.parseDouble(valoresClassificados.get(flag2)));
					tipo.getEixos().add(eixo);
					flag++;
					flag2 = flag + 7;
				}
				flag = flag + 7;
				flag2 = flag + 7;
				conteudo.getTipos().add(tipo);
			}
			conteudos.add(conteudo);

		}
		informacoes = conteudos;
	}
}
