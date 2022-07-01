package com.bsoft.tabelaFrete.utils;

import java.util.ArrayList;
import java.util.List;

public class ListasUtil {
    public static List<String> palavrasParaRetirar = new ArrayList<String>() {
        {
            add("II");
            add("Art");
            add("Diretoria");
            add("nº");
            add("#");
            add("Número");
            add("Coeficiente");
            add("COEFICIENTE");
            add("unidade");
            add("Nota");
            add("Brasil");
        }
    };

    public static List<String> regExFiltrarValores = new ArrayList<String>() {
        {
            add("\\d.\\d\\d\\d\\d");
            add("\\d\\d.\\d\\d\\d\\d");
            add("\\d\\d.\\d\\d");
            add("\\d\\d\\d.\\d\\d");
        }
    };

    public static List<String> popularTipos = new ArrayList<String>() {
        {
            add("granelSolido");
            add("granelLiquido");
            add("frigorificada");
            add("conteinerizada");
            add("cargaGeral");
            add("neogranel");
            add("perigosaGranelSolido");
            add("perigosaGranelLiquido");
            add("perigosaCargaFrigorificada");
            add("perigosaConteinerizada");
            add("perigosaCargaGeral");
            add("granelPressurizada");

        }
    };

    public static List<String> popularTiposSemFiltro = new ArrayList<String>() {
        {
            add("Granel sólido");
            add("Granel líquido");
            add("Frigorificada ou Aquecida");
            add("Conteinerizada");
            add("Carga Geral");
            add("Neogranel");
            add("Perigosa (granel sólido)");
            add("Perigosa (granel líquido)");
            add("Perigosa (frigorificada ou aquecida)");
            add("Perigosa (conteinerizada)");
            add("Perigosa (carga geral)");
            add("Carga Granel Pressurizada");
        }
    };

    public static List<String> popularEixos = new ArrayList<String>() {
        {
            add("eixo2");
            add("eixo3");
            add("eixo4");
            add("eixo5");
            add("eixo6");
            add("eixo7");
            add("eixo9");
        }
    };

    public static List<String> titulos = new ArrayList<String>() {
        {
            add("lotacao");
            add("somenteAutomotor");
            add("lotacaoAltoDesempenho");
            add("somenteAutomotorAltoDesempenho");
        }
    };
}
