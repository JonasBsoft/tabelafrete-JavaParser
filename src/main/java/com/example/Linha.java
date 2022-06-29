package com.example;

import java.util.HashMap;

public class Linha {
    private HashMap<String, String> eixos_deslocamento = new HashMap<>(); // OK
    private HashMap<String, String> eixos_carga_descarga = new HashMap<>(); // OK

    private int pegarProximoEixo(int i) {
        if (i == 7) {
            i = 9;
            return i;
        }
        if (i == 9) {
            i = 2;
            return i;
        }
        i = i + 1;
        return i;
    }

    public String pegarValores(HashMap<String, String> precoPorEixo) {
        int i = 1;
        String valores = "";
        while (i < 10) {
            i = pegarProximoEixo(i);
            valores = valores + ", " + precoPorEixo.get(String.valueOf(i));
        }
        return valores;
    }

    public String valoresEixoDeslocamento() {
        return pegarValores(this.eixos_deslocamento);
    }

    public HashMap<String, String> getEixos_deslocamento() {
        return this.eixos_deslocamento;
    }

    public void setEixos_deslocamento(HashMap<String, String> eixos_deslocamento) {

        this.eixos_deslocamento = eixos_deslocamento;
    }

    public void addEixos_deslocamento(String eixo, String valor) {
        this.eixos_deslocamento.put(eixo, valor);
    }

    public void addEixos_CargaeDescarga(String eixo, String valor) {
        this.eixos_carga_descarga.put(eixo, valor);
    }

    public String valoresCargaeDescarga() {
        return pegarValores(this.eixos_carga_descarga);
    }

    public void setEixos_carga_descarga(HashMap<String, String> eixos_carga_descarga) {
        this.eixos_carga_descarga = eixos_carga_descarga;
    }

    public HashMap<String, String> getEixos_carga_descarga() {
        return this.eixos_carga_descarga;
    }

}
