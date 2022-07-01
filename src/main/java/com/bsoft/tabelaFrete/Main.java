package com.bsoft.tabelaFrete;

import com.bsoft.tabelaFrete.TratamentoDados.Aramazena;
import com.bsoft.tabelaFrete.TratamentoDados.Classificar;
import com.bsoft.tabelaFrete.TratamentoDados.PegarValores;
import com.bsoft.tabelaFrete.utils.Conexao;

public class Main{
    public static void main(String[] args) {
        Conexao.conectar();
		PegarValores.adquirirValores();
		Classificar.classificarCampos();
		Aramazena.armazenarConteudo();
		GerarJson.gerarArquivoJSON();
    }
}