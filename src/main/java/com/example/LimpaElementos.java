package com.example;

import java.util.ArrayList;
import java.util.List;

public class LimpaElementos {
    static List<String> palavrasParaRetirar = new ArrayList<String>(){
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

    static List<String> replaceTipos = new ArrayList<String>(){
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
}
