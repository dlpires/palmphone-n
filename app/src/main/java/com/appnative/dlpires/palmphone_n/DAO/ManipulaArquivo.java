package com.appnative.dlpires.palmphone_n.DAO;

import android.content.Context;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dlpires on 23/02/2018.
 */
//CLASSE PARA ACESSO A ARQUIVOS DO DISPOSITIVO
public class ManipulaArquivo {

    //MÉTODO QUE CRIA O ARQUIVO E GRAVA OS DADOS
    public void gravarArquivo(String nomeArquivo, String json, Context context, String emailUser){
        try {
            File root = new File(context.getFilesDir(),emailUser);
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
    public String lerArquivo(String nomeArquivo, Context context, String emailUser){
        //BUSCANDO ARQUIVO
        File root = new File(context.getFilesDir(),emailUser);
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
    public void apagarArquivo(Context context, String emailUser, String nomeArquivo){
        //BUSCANDO ARQUIVO
        File root = new File(context.getFilesDir(),emailUser);
        File file = new File(root, nomeArquivo);
        //APAGANDO ARQUIVO
        file.delete();
    }

    //MÉTODO PARA VERIFICAR SE EXISTE ARQUIVOS SALVOS
    public boolean existeArquivo(Context context, String emailUser){

        //BUSCANDO ARQUIVO
        File root = new File(context.getFilesDir(),emailUser);

        if(!root.exists()) //VERIFICANDO SE A PASTA EXISTE
            return false;

        if(root.listFiles().length <= 1) //VERIFICANDO SE EXISTE ARQUIVOS DE CHAMADA NA PASTA
            return false;

        return true;
    }
}
