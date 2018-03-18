package com.appnative.dlpires.palmphone_n.DAO;

import android.content.Context;
import android.support.annotation.NonNull;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by dlpires on 23/02/2018.
 */
//CLASSE PARA ACESSO A ARQUIVOS DO DISPOSITIVO
public class ManipulaArquivo {

    private static FirebaseAuth auth = ConnectFirebase.getFirebaseAuth();

    //MÉTODO QUE CRIA O ARQUIVO E GRAVA OS DADOS
    public static void gravarArquivo(String nomeArquivo, String json, Context context){
        try {
            File root = new File(context.getFilesDir(), auth.getCurrentUser().getEmail());
            //VERIFICA SE PASTA EXISTE NO CAMINHO, ONDE NÃO EXISTINDO A PASTA É CRIADA
            if (!root.exists()) {
                root.mkdir();
            }
            //CRIANDO O NOVO ARQUIVO
            File jsonFile = new File(root, nomeArquivo);
            jsonFile.createNewFile();

            //ABRINDO PARA GRAVAÇÃO
            FileWriter writer = new FileWriter(jsonFile);
            //GRAVANDO DADOS
            writer.append(json);
            //LIMPANDO OBJETO E FECHANDO ARQUIVO
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            NotificaUser.alertaToast(context, "Não foi possivel criar/gravar o arquivo!");
        }
    }

    //MÉTODO PARA LER ARQUIVO
    @NonNull
    public static String lerArquivo(Context context){
        //BUSCANDO ARQUIVO
        File root = new File(context.getFilesDir(), auth.getCurrentUser().getEmail());
        File file = new File(root, auth.getCurrentUser().getUid() + ".json");
        StringBuilder text = new StringBuilder();

        try {

            //OBJETO PARA LER O ARQUIVO
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            //LOOP PARA LER AS LINHAS DO ARQUIVO
            while ((line = br.readLine()) != null) {
                text.append(line);
            }

            //FECHANDO O ARQUIVO
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        //RETORNANDO STRING
        return text.toString();
    }

    //MÉTODO PARA LER ARQUIVO (SOBRESCRITA)
    @NonNull
    public static String lerArquivo(String nomeArquivo, Context context){
        //BUSCANDO ARQUIVO
        File root = new File(context.getFilesDir(), auth.getCurrentUser().getEmail());
        File file = new File(root, nomeArquivo);
        StringBuilder text = new StringBuilder();

        try {

            //OBJETO PARA LER O ARQUIVO
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            //LOOP PARA LER AS LINHAS DO ARQUIVO
            while ((line = br.readLine()) != null) {
                text.append(line);
            }

            //FECHANDO O ARQUIVO
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        //RETORNANDO STRING
        return text.toString();
    }

    //MÉTODO PARA APAGAR ARQUIVO
    public static void apagarArquivo(Context context, String nomeArquivo){
        //BUSCANDO ARQUIVO
        File root = new File(context.getFilesDir(), auth.getCurrentUser().getEmail());
        File file = new File(root, nomeArquivo);
        //APAGANDO ARQUIVO
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MÉTODO PARA VERIFICAR SE EXISTE ARQUIVOS SALVOS
    public static boolean existeArquivo(Context context){

        //BUSCANDO ARQUIVO
        File root = new File(context.getFilesDir(), auth.getCurrentUser().getEmail());

        if(!root.exists()) //VERIFICANDO SE A PASTA EXISTE
            return false;

        if(root.listFiles().length <= 2) //VERIFICANDO SE EXISTE ARQUIVOS DE CHAMADA NA PASTA
            return false;

        return true;
    }
}
