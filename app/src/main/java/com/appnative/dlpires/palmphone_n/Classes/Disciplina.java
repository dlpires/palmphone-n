package com.appnative.dlpires.palmphone_n.Classes;

import java.util.ArrayList;

/**
 * Created by root on 14/01/18.
 */

public class Disciplina {
    private int codDisc;
    private String nomeDisc;
    private String siglaDisc;
    private ArrayList <Aluno> alunos;
    private ArrayList <Chamada> chamadas;

    public int getCodDisc() {
        return codDisc;
    }

    public void setCodDisc(int codDisc) {
        this.codDisc = codDisc;
    }

    public String getNomeDisc() {
        return nomeDisc;
    }

    public void setNomeDisc(String nomeDisc) {
        this.nomeDisc = nomeDisc;
    }

    public String getSiglaDisc() {
        return siglaDisc;
    }

    public void setSiglaDisc(String siglaDisc) {
        this.siglaDisc = siglaDisc;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    public ArrayList<Chamada> getChamadas() {
        return chamadas;
    }

    public void setChamadas(ArrayList<Chamada> chamadas) {
        this.chamadas = chamadas;
    }
}
