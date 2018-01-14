package com.appnative.dlpires.palmphone_n.Classes;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 14/01/18.
 */

public class Chamada {
    private int numAulaChamada;
    private Date dtChamada;
    private Time hrChamada;
    private ArrayList <Aluno> alunos;

    public int getNumAulaChamada() {
        return numAulaChamada;
    }

    public void setNumAulaChamada(int numAulaChamada) {
        this.numAulaChamada = numAulaChamada;
    }

    public Date getDtChamada() {
        return dtChamada;
    }

    public void setDtChamada(Date dtChamada) {
        this.dtChamada = dtChamada;
    }

    public Time getHrChamada() {
        return hrChamada;
    }

    public void setHrChamada(Time hrChamada) {
        this.hrChamada = hrChamada;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }
}
