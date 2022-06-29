package com.example;

import java.util.HashMap;

public class Linha {
    private String titulo; // OK
    private String tipo = ""; // OK
    private String coeficiente_1 = "custo_km"; // OK
    private String coeficiente_2 = "carga_descarga"; // OK
    private String unidade_1 = "R$/km"; // OK
    private String unidade_2 = "R$"; // OK
    private HashMap<String, String> eixos_deslocamento = new HashMap<>(); // OK
    private HashMap<String, String> eixos_carga_descarga = new HashMap<>(); // OK

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCoeficiente_1() {
        return this.coeficiente_1;
    }

    public void setCoeficiente_1(String coeficiente_1) {
        this.coeficiente_1 = coeficiente_1;
    }

    public String getCoeficiente_2() {
        return this.coeficiente_2;
    }

    public void setCoeficiente_2(String coeficiente_2) {
        this.coeficiente_2 = coeficiente_2;
    }

    public String getUnidade_1() {
        return this.unidade_1;
    }

    public void setUnidade_1(String unidade_1) {
        this.unidade_1 = unidade_1;
    }

    public String getUnidade_2() {
        return this.unidade_2;
    }

    public void setUnidade_2(String unidade_2) {
        this.unidade_2 = unidade_2;
    }

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

    @Override
    public String toString() {

        return this.titulo + " " + this.tipo + " " + this.coeficiente_1 + " " + this.unidade_1 + " "
                + valoresEixoDeslocamento() + " " + this.coeficiente_2 + " " + this.unidade_2 + " "
                + this.valoresCargaeDescarga();
    }

}
