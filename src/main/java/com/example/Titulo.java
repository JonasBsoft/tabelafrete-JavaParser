package com.example;

import java.util.ArrayList;
import java.util.List;

public class Titulo {

    private String nome;
    private List<Tipo> tipos = new ArrayList<>();

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Tipo> getTipos() {
        return this.tipos;
    }

    public void addTipo(Tipo tipo) {
        this.tipos.add(tipo);
    }

}
