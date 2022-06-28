package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import com.example.Linha;
import com.example.tratamentoDados.PegarTipos;

public class ValorEmLinha {

    static List<String> tipos = new ArrayList<>();
	static List<Linha> linhas = new ArrayList<>();
	static int eixo = 1;
	static Linha linhaAtual = new Linha();
	static boolean coeficienteisDeslocamento = false;

    public static String adicionarValorEmLinha(String elemento) {
		String eixo = String.valueOf(getEixo());

		if (coeficienteisDeslocamento) {
			linhaAtual.addEixos_deslocamento(eixo, elemento);

		}
		linhaAtual.addEixos_CargaeDescarga(eixo, elemento);
		return "[" + eixo + "]VALORES: " + elemento;
	}

	public static void adicionarLinhaEmTabela(Linha linhaAtual) {
		if (!linhaAtual.equals(null)) {

			linhas.add(linhaAtual);
		} // se nao for null:

	}

	public static boolean isTipo(String elemento) {
		PegarTipos.getTipos();
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
