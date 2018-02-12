package com.appnative.dlpires.palmphone_n.Classes;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 14/01/18.
 */

//CLASSE PROFESSOR
public class Professor {
    //ATRIBUTOS
    private int codProf;
    private String nomeProf;
    private String emailProf;
    private String senhaProf;
    private String rgProf;
    private String dataNascProf;
    private ArrayList <Disciplina> disciplinas;

    //CONSTRUTORES
    public Professor(){

    }

    public Professor(int codProf, String nomeProf, String emailProf, String senhaProf, String rgProf, String dataNascProf, ArrayList<Disciplina> disciplinas) {
        this.codProf = codProf;
        this.nomeProf = nomeProf;
        this.emailProf = emailProf;
        this.senhaProf = senhaProf;
        this.rgProf = rgProf;
        this.dataNascProf = dataNascProf;
        this.disciplinas = disciplinas;
    }

    //GETTERS E SETTERS
    public int getCodProf() {
        return codProf;
    }

    public void setCodProf(int codProf) {
        this.codProf = codProf;
    }

    public String getNomeProf() {
        return nomeProf;
    }

    public void setNomeProf(String nomeProf) {
        this.nomeProf = nomeProf;
    }

    public String getEmailProf() {
        return emailProf;
    }

    public void setEmailProf(String emailProf) {
        this.emailProf = emailProf;
    }

    public String getSenhaProf() {
        return senhaProf;
    }

    public void setSenhaProf(String senhaProf) {
        this.senhaProf = senhaProf;
    }

    public String getRgProf() {
        return rgProf;
    }

    public void setRgProf(String rgProf) {
        this.rgProf = rgProf;
    }

    public String getDataNascProf() {
        return dataNascProf;
    }

    public void setDataNascProf(String dataNascProf) {
        this.dataNascProf = dataNascProf;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
