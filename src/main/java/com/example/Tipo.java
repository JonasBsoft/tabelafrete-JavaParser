package com.example;

import java.util.ArrayList;
import java.util.List;

public class Tipo {
    private String nome;
    private List<Eixos> eixos = new ArrayList();

    public List<Eixos> getEixos() {
        return this.eixos;
    }

    public void addEixo(Eixos eixo) {
        this.eixos.add(eixo);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}