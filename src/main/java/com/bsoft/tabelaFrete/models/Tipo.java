package com.bsoft.tabelaFrete.models;

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

    public void add(Eixo eixo) {
        eixos.add(eixo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
