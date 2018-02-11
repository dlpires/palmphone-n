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
    private Date dataNascProf;
    private ArrayList <Disciplina> disciplinas;

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

    public Date getDataNascProf() {
        return dataNascProf;
    }

    public void setDataNascProf(Date dataNascProf) {
        this.dataNascProf = dataNascProf;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
