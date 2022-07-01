package com.example;

import java.util.ArrayList;
import java.util.List;

public class Tipo {
    private String nome;
    private List<Eixo> eixos;

    public Tipo() {
        this.eixos = new ArrayList<>();
    }

    public List<Eixo> getEixos() {
        return eixos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
