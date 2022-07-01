package com.example;

public class Eixo {
    private String nomeEixo;
    private double deslocamento;
    private double carga;

    public void setCarga(double carga) {
        this.carga = carga;
    }

    public void setDeslocamento(double deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setNomeEixo(String nomeEixo) {
        this.nomeEixo = nomeEixo;
    }

    public double getCarga() {
        return carga;
    }

    public double getDeslocamento() {
        return deslocamento;
    }

    public String getNomeEixo() {
        return nomeEixo;
    }
}
