package com.example;

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

    public String getTitulo() {
        return titulo;
    }

}
