package com.example;

import java.util.HashMap;

public class Linha {
    private HashMap<String, String> eixos_deslocamento = new HashMap<>(); // OK
    private HashMap<String, String> eixos_carga_descarga = new HashMap<>(); // OK

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

    public void setEixos_carga_descarga(HashMap<String, String> eixos_carga_descarga) {
        this.eixos_carga_descarga = eixos_carga_descarga;
    }

    public HashMap<String, String> getEixos_carga_descarga() {
        return this.eixos_carga_descarga;
    }

}
