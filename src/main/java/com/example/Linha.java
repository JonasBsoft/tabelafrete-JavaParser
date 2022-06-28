package com.example;


import java.util.HashMap;



public class Linha {
    private String titulo;
    private String tipo;
    private String coeficiente_1 = "Deslocamento (CCD)";
    private String coeficiente_2 = "Carga e descarga (CC)";
    private String unidade_1 = "R$/km";
    private String unidade_2 = "R$";
    private HashMap<String, String> eixos_deslocamento = new HashMap<>();
    private HashMap<String, String> eixos_carga_descarga = new HashMap<>();
    static int EIXO = 7;

    public void adicionarValor(String valor) {

        // "9", "122,32"
        // "2", "323232"
     /*    if (EIXO != 2) {

            if (EIXO == 9) {
                EIXO = 7;
            } else {
                EIXO--;

            }
        } else {
            EIXO = 9;
        }*/

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

    public HashMap<String, String> getEixos_deslocamento() {
        return this.eixos_deslocamento;
    }

    public void setEixos_deslocamento(HashMap<String, String> eixos_deslocamento) {
        this.eixos_deslocamento = eixos_deslocamento;
    }

    public HashMap<String, String> getEixos_carga_descarga() {
        return this.eixos_carga_descarga;
    }

    public void setEixos_carga_descarga(HashMap<String, String> eixos_carga_descarga) {
        this.eixos_carga_descarga = eixos_carga_descarga;
    }

}
