package com.example;

import java.util.ArrayList;

import java.util.List;

public class Eixos {
    private String nome;
    private List<EixoValor> eixos = new ArrayList();

    public List<EixoValor> getEixos() {
        return this.eixos;
    }

    public void add(EixoValor eixo) {
        this.eixos.add(eixo);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEixos(List<EixoValor> eixos) {
        this.eixos = eixos;
    }

}