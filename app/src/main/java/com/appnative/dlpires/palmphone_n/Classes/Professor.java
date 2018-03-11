package com.appnative.dlpires.palmphone_n.Classes;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.appnative.dlpires.palmphone_n.DAO.CrudFirebase;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 14/01/18.
 */

//CLASSE PROFESSOR
public class Professor {
    //ATRIBUTOS
    @Exclude
    private int codProf;

    private String nomeProf;
    private String emailProf;
    private String senhaProf;
    private String rgProf;
    private String dataNascProf;
    private String url;
    private List<String> disciplinas;

    //CONSTRUTOR
    public Professor(){
        disciplinas = new ArrayList<String>();
    }

    //GETTERS E SETTERS
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Exclude
    public int getCodProf() {
        return codProf;
    }

    @Exclude
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

    public List<String> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<String> disciplinas) {
        this.disciplinas = disciplinas;
    }


    //MÉTODO PARA CADASTRAR USUÁRIO
    public void create(Context context, Uri imageUri){
        //INSTANCIANDO OBJETO PARA REALIZAR A PERSISTENCIA DE DADOS
        CrudFirebase crud = new CrudFirebase(this);

        //MÉTODOS DE CADASTRO
        crud.createUser(context, imageUri);
    }

    //MÉTODO PARA SALVAR OBJETO EM ARQUIVO JSON
    public void createJson(){

    }
}
