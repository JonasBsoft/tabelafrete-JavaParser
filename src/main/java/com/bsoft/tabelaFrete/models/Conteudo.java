package com.bsoft.tabelaFrete.models;

import java.util.ArrayList;
import java.util.List;

public class Conteudo {

    private String titulo;
    private List<Tipo> tipos;

    public Conteudo() {
        this.tipos = new ArrayList<>();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void add(Tipo tipo) {
        tipos.add(tipo);

    }

    public String getTitulo() {
        return titulo;
    }

}
