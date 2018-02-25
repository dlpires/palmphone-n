package com.appnative.dlpires.palmphone_n.Classes;

import java.util.ArrayList;

/**
 * Created by root on 14/01/18.
 */
//CLASSE CHAMADA
public class Chamada {
    //ATRIBUTOS
    private int numAulaChamada;
    private String dtChamada;
    private String hrChamada;
    private ArrayList <Aluno> alunos;

    //GETTERS E SETTERS
    public int getNumAulaChamada() {
        return numAulaChamada;
    }

    public void setNumAulaChamada(int numAulaChamada) {
        this.numAulaChamada = numAulaChamada;
    }

    public String getDtChamada() {
        return dtChamada;
    }

    public void setDtChamada(String dtChamada) {
        this.dtChamada = dtChamada;
    }

    public String getHrChamada() {
        return hrChamada;
    }

    public void setHrChamada(String hrChamada) {
        this.hrChamada = hrChamada;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }
}
