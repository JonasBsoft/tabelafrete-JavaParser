package com.bsoft.tabelaFrete.models;

public class Eixo {
    private String nome;
    private double deslocamento;
    private double carga;

    public void setCarga(double carga) {
        this.carga = carga;
    }

    public void setDeslocamento(double deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setNome(String nomeEixo) {
        this.nome = nomeEixo;
    }

    public double getCarga() {
        return carga;
    }

    public double getDeslocamento() {
        return deslocamento;
    }

    public String getNome() {
        return nome;
    }
}
