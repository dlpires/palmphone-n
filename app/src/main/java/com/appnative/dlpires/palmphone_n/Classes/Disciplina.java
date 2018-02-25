package com.appnative.dlpires.palmphone_n.Classes;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by root on 14/01/18.
 */

//CLASSE DISCIPLINA
public class Disciplina {
    //ATRIBUTOS
    @Exclude
    private int codDisc;
    private String dscDisc;
    private ArrayList <Aluno> alunos;
    private ArrayList <Chamada> chamadas;

    //GETTERS E SETTERS
    public int getCodDisc() {
        return codDisc;
    }

    public void setCodDisc(int codDisc) {
        this.codDisc = codDisc;
    }

    public String getDscDisc() {
        return dscDisc;
    }

    public void setNomeDisc(String dscDisc) {
        this.dscDisc = dscDisc;
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
