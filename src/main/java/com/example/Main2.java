package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.utils.ListasUtil;

public class Main2 {

    static Document doc;
    static String url = "https://www.in.gov.br/en/web/dou/-/resolucao-n-5.959-de-20-de-janeiro-de-2022-375504795";
    static List<String> elementos = new ArrayList<>();
    static boolean coeficienteisDeslocamento = false;
    static List<String> valores = new ArrayList<>();
    static List<Conteudo> conteudos;

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        conectar(); // conecta ao sit
        getValores();
        classificaCampo();
        popularConteudo();
        gerarJSON();
    }

    public static void conectar() {
        try {
            doc = Jsoup.connect(Main2.url).get();
        } catch (Exception e) {
        }
    }

    public static void getValores() {
        List<String> elementosTabelas = new ArrayList<>();
        try {
            for (Element e : doc.select(".dou-paragraph")) {
                String text = e.text();
                if (limparElementos(text)) {
                    elementosTabelas.add(text);
                }
            }
            elementos = elementosTabelas;
        } catch (Exception e) {
        }
    }

    public static void popularConteudo() {
        List<Conteudo> conteudos = new ArrayList<>();
        int flag = 0;
        int flag2 = flag + 7;
        for (String titulo : ListasUtil.titulos) {
            Conteudo conteudo = new Conteudo();
            conteudo.setTitulo(titulo);
            for (String tipos : ListasUtil.popularTipos) {
                Tipo tipo = new Tipo();
                tipo.setNome(tipos);
                for (String eixos : ListasUtil.popularEixos) {
                    Eixo eixo = new Eixo();
                    eixo.setNomeEixo(eixos);
                    System.out.println(flag + ", " + flag2);
                    eixo.setDeslocamento(valores.get(flag));
                    eixo.setCarga(valores.get(flag2));
                    tipo.getEixos().add(eixo);
                    flag++;
                    flag2 = flag + 7;
                }
                flag = flag + 7;
                flag2 = flag + 7;
                conteudo.getTipos().add(tipo);
            }
            conteudos.add(conteudo);
            System.out.println("Flag: " + flag);
        }
        Main2.conteudos = conteudos;
    }

    public static boolean limparElementos(String elemento) {
        if (elemento.length() == 1) {
            return false;
        }

        for (String string : ListasUtil.palavrasParaRetirar) {
            if (elemento.contains(string)) {
                return false;
            }
        }
        return true;
    }

    public static void classificaCampo() {
        int i = 1;
        for (String elemento : elementos) {
            if (elemento.length() == 0) {
                if (elementos.get(i).length() == 0) {

                    if (elementos.get(i + 1).length() == 0) {
                        elemento = elementos.get(i + 2);
                    } else {
                        elemento = elementos.get(i + 1);
                    }
                } else {
                    elemento = elementos.get(i);
                }
            }

            for (String regex : ListasUtil.regexs) {
                if (elemento.matches(regex)) {
                    elemento = elemento.replace(",", ".");
                    valores.add(elemento);
                }
            }
            i++;
        }
    }

    public static void gerarJSON() {
        JSONArray arquivoPronto = new JSONArray();
        JSONObject titulosJSON = new JSONObject();
        for (Conteudo titulo : conteudos) {// lista de Titulos
            JSONObject tiposJSON = new JSONObject();
            for (Tipo tipo : titulo.getTipos()) {
                JSONObject eixoObj = new JSONObject();
                for (Eixo eixo : tipo.getEixos()) {
                    JSONObject eixoValorJSON = new JSONObject();
                    eixoValorJSON.put("Carga_Descarga", eixo.getCarga());
                    eixoValorJSON.put("Deslocamento", eixo.getDeslocamento());
                    eixoObj.put(eixo.getNomeEixo(), eixoValorJSON);
                }
                tiposJSON.put(tipo.getNome(), eixoObj);
            }
            titulosJSON.put(titulo.getTitulo(), tiposJSON);
        }
        arquivoPronto.add(titulosJSON);

        try (FileWriter file = new FileWriter("tabelafrete.json")) {
            file.write(arquivoPronto.toJSONString());
            file.flush();
            System.out.println("Arquivo Gerado");
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo");
            e.printStackTrace();
        }
    }
}