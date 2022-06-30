package com.example.utils;

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

    public static List<String> replaceTipos = new ArrayList<String>() {
        {
            add("Carga e descarga");
            add("()!");
            add("!");
            add("     )");
            add("  ");
            add("Deslocamento");
            add("(!");
            add("(CCD)");
            add("(CC)");
        }
    };

    public static List<String> regexs = new ArrayList<String>() {
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
            add("Carga Geral");
            add("Granel");
            add("Frigorificada ou Aquecida");
            add("Perigosa (conteinerizada)");
            add("Perigosa (frigorificada ou aquecida)");
            add("Perigosa (granel líquido)");
            add("Perigosa (granel sólido)");
            add("Neogranel");
            add("Conteinerizada");
            add("Granel líquido");
            add("Granel sólido");
            add("Carga Granel Pressurizada");
            add("Perigosa (carga geral)");

        }
    };

    public static List<String> popularEixos = new ArrayList<String>() {
        {
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("9");
        }
    };
}
