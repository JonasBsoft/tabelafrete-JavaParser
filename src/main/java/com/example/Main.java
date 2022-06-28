package com.example;

import com.example.tratamentoDados.PegarTipos;
import com.example.tratamentoDados.PegarTitulos;
import com.example.tratamentoDados.PegarValores;
import com.example.tratamentoDados.classficaCampos;

public class Main {
	

	public static void main(String[] args) {
		PegarTitulos.getTitulos(); //está ok
		PegarTipos.getTipos(); //ok 
		PegarValores.getValores(); //ok
		classficaCampos.classificaCampo();
	}
}
