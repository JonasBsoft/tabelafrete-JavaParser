package com.bsoft.tabelaFrete;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bsoft.tabelaFrete.TratamentoDados.Aramazena;
import com.bsoft.tabelaFrete.models.Conteudo;
import com.bsoft.tabelaFrete.models.Eixo;
import com.bsoft.tabelaFrete.models.Tipo;




public class GerarJson {
    
    public static void gerarArquivoJSON() {
		JSONArray arquivoPronto = new JSONArray();
		JSONObject titulosJSON = new JSONObject();
		for (Conteudo titulo : Aramazena.informacoes) {// lista de Titulos
			JSONObject tiposJSON = new JSONObject();
			for (Tipo tipo : titulo.getTipos()) {
				JSONObject eixoObj = new JSONObject();
				for (Eixo eixo : tipo.getEixos()) {
					JSONObject eixoValorJSON = new JSONObject();
					eixoValorJSON.put("carga_descarga", eixo.getCarga());
					eixoValorJSON.put("custo_km", eixo.getDeslocamento());
					eixoObj.put(eixo.getNomeEixo(), eixoValorJSON);
				}
				tiposJSON.put(tipo.getNome(), eixoObj);
			}
			titulosJSON.put(titulo.getTitulo(), tiposJSON);
		}
		arquivoPronto.add(titulosJSON);

		try (FileWriter file = new FileWriter("tabelafrete.json")) {
			file.write(arquivoPronto.toJSONString());
			file.flush();
			System.out.println("Arquivo Gerado");
		} catch (IOException e) {
			System.out.println("Erro ao gerar arquivo");
			e.printStackTrace();
		}
	}
}
