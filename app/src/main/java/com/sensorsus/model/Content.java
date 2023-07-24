package com.sensorsus.model;

import java.util.ArrayList;

public class Content {
    public int id;
    public String nome;
    public int codCnes;
    public String descricao;
    public String orgaoGestor;
    public String naturezaJuridica;
    public Endereco endereco;
    public ArrayList<Object> telefones;
    public double score;
    public int count;
    public ArrayList<Score> scores;
}
