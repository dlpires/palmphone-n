package com.appnative.dlpires.palmphone_n.Classes;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.appnative.dlpires.palmphone_n.Activity.ColetorPage;
import com.appnative.dlpires.palmphone_n.DAO.FirebaseAuthDAO;
import com.appnative.dlpires.palmphone_n.DAO.FirebaseDatabaseDAO;
import com.appnative.dlpires.palmphone_n.DAO.FirebaseStorageDAO;
import com.appnative.dlpires.palmphone_n.DAO.ManipulaArquivo;
import com.google.firebase.database.Exclude;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        //MÉTODOS DE CADASTRO
        FirebaseAuthDAO.createUser(context, imageUri, this);
    }

    //MÉTODO PARA SALVAR OBJETO EM ARQUIVO JSON
    public void createJson(Context context){
        FirebaseDatabaseDAO.infUserLogado(context);
    }

    //MÉTODO PARA LER AS INFORMAÇÕES DO OBJETO
    public Professor readJson(Context context){
        //CHAMANDO OBJETO: BIBLIOTECA JSON
        Gson gson = new Gson();
        //PEGANDO STRING JSON
        String jsonDisc = ManipulaArquivo.lerArquivo(context);

        //INSTANCIANDO VALORES
        //PEGANDO ARRAY COM OS NOMES DAS DISCIPLINAS
        return gson.fromJson(jsonDisc, Professor.class);
    }

    //MÉTODO PARA SALVAR UMA DISCIPLINA EM UM ARQUIVO LOCAL
    public void setDisciplinaArq(String selectedItem, Context context){
        //SALVANDO DISCIPLINA SELECIONADA NO SPINNER
        ManipulaArquivo.gravarArquivo("disciplina.txt", selectedItem, context);
    }

    //MÉTODO PARA SALVAR CHAMADA
    public void createChamada(Context context, Professor professor){
        FirebaseDatabaseDAO.lerChamada(context, (ArrayList<String>) professor.getDisciplinas());
    }

    //MÉTODO PARA VALIDAR LOGIN NO SISTEMA
    public boolean login(Context context){
        if(FirebaseAuthDAO.loginUser(this, context)){
            return true;
        }
        return false;
    }

    //MÉTODO PARA RESGATAR IMAGEM DE PERFIL
    public void readImgPerfil(Context context, ImageView imageView){
        FirebaseStorageDAO.carregaImagemPerfilUser(context, imageView);
    }


}
