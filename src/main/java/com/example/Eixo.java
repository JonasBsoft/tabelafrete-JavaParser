package com.example;

public class Eixo {
    private String nomeEixo;
    private String deslocamento;
    private String carga;

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public void setDeslocamento(String deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setNomeEixo(String nomeEixo) {
        this.nomeEixo = nomeEixo;
    }

    public String getCarga() {
        return carga;
    }

    public String getDeslocamento() {
        return deslocamento;
    }

    public String getNomeEixo() {
        return nomeEixo;
    }
}
