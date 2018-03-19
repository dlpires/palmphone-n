package com.appnative.dlpires.palmphone_n.Classes;

import android.content.Context;

import com.appnative.dlpires.palmphone_n.Activity.ColetorDadosPage;
import com.appnative.dlpires.palmphone_n.DAO.ManipulaArquivo;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dlpires on 24/02/2018.
 */

//CLASSE RA
public class Ra {
    //ATRIBUTOS DA CLASSE
    private String ra;
    private String data;


    //GETTERS E SETTERS
    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void salvarArrayRa(Context context, ArrayList<Ra> ras){
        //INICIALIZANDO OBJETO GSON
        Gson gson = new Gson();

        //FORMANDO NOME DO ARQUIVO A SER SALVO
        String nomeArquivo = ManipulaArquivo.lerArquivo("disciplina.txt", context) + ".json";

        //PEGANDO STRING EM FORMATO JSON
        String json = gson.toJson(ras);

        //SALVANDO O ARQUIVO
        ManipulaArquivo.gravarArquivo(nomeArquivo, json, context);

        //NOTIFICANDO O USUÁRIO
        NotificaUser.alertaToast(context,"Chamada Finalizada!");
    }

    //MÉTODO PARA SALVAR RA NO ARQUIVO JSON
    public Ra salvarRa(String codigo){

        //INSTANCIANDO OBJETO DATE
        Date date = new Date();
        //INSTANCIANDO OBJETO PARA SALVAR NO ARRAY RAS
        Ra r = new Ra();

        //SALVANDO INFORMAÇÕES NO OBJETO
        r.setData(new SimpleDateFormat("dd/MM/yyyy/-hh-mm-ss").format(date));
        r.setRa(codigo);

        return r;
    }
}
