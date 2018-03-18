package com.appnative.dlpires.palmphone_n.DAO;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.appnative.dlpires.palmphone_n.Activity.MenuPage;
import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.Classes.Ra;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/03/18.
 */

public class FirebaseDatabaseDAO {


    //REFERENCIAS NECESSÁRIAS
    private static FirebaseDatabase database = ConnectFirebase.getFirebaseDatabase();
    private static DatabaseReference databaseReference = ConnectFirebase.getFirebaseDbRef();
    private static Gson gson = new Gson();

    //ADQUIRINDO USUÁRIO
    private static FirebaseAuth auth = ConnectFirebase.getFirebaseAuth();


    //MÉTODO PARA ARMAZENAR INFORMAÇÕES DO USUÁRIO LOGADO EM UM ARQUIVO JSON
    public static void infUserLogado(final Context context){

        //FAZENDO ACESSO A BASE DE DADOS DO FIREBASE, BUSCANDO PELO UID DO USUÁRIO AS SUAS INFORMAÇÕES (CHAVE NO DATABASE
        // É IDENTIFICADO PELO USUÁRIO)
        databaseReference.child("professor").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Professor prof = dataSnapshot.getValue(Professor.class);
                //PEGANDO INFORMAÇÕES DO JSON E INSERINDO NAS CAIXAS DE TEXTO
                ManipulaArquivo.gravarArquivo(auth.getCurrentUser().getUid() + ".json", gson.toJson(prof), context);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //VERIFICA SE USUÁRIO ESTA LOGADO, PARA MOSTRAR A MENSAGEM DE ERRO APENAS QUANDO O USUÁRIO ESTA LOGADO MAS A CONEXÃO
                //COM O BANCO FOI PERDIDA
                if(FirebaseAuthDAO.usuarioLogado())
                    NotificaUser.alertaToast(context, databaseError.toString());
            }
        });
    }

    //MÉTODO PARA INSERIR INFORMAÇÕES DO USUÁRIO NO BANCO DE DADOS
    public static void insereUsuario(Context context, Professor professor){
        try{
            //INSERINDO URL DA IMAGEM DE PERFIL NA VARIAVEL ESTATICA
            professor.setUrl(ManipulaArquivo.lerArquivo("linkImgPerfil.txt", context));

            //APAGANDO O ARQUIVO DA PASTA
            ManipulaArquivo.apagarArquivo(context, "linkImgPerfil.txt");

            //INSERINDO DADOS NO NÓ PROFESSOR
            databaseReference.child("professor").child(auth.getCurrentUser().getUid())
                    .setValue(professor);//PUXANDO A CHAVE PRIMARIA E INSERINDO USUARIO
        }
        catch(Exception e){
            NotificaUser.alertaToast(context, "Erro ao Gravar Usuário!");
            e.printStackTrace();
        }
    }

    public static void lerChamada(final Context context, ArrayList<String> disciplinas){
        Gson gson = new Gson();
        String discChamada = ManipulaArquivo.lerArquivo("disciplina.txt", context);
        //LENDO O ARRAY
        for (String s : disciplinas) {
            if(s.equals(discChamada)){
                //PUXANDO NO ARRAY E SALVANDO O ARQUIVO
                ArrayList<Ra> chamadas = gson.fromJson(ManipulaArquivo.lerArquivo(discChamada + ".json", context), new TypeToken<List<Ra>>(){}.getType());
                //SALVANDO NO FIREBASE
                salvarChamada(chamadas);
                //APAGANDO O ARQUIVO SALVO
                ManipulaArquivo.apagarArquivo(context, discChamada + ".json");
            }
        }

        //APAGANDO O ARQUIVO DISCIPLINA.TXT
        ManipulaArquivo.apagarArquivo(context, "disciplina.txt");

        NotificaUser.alertaToast(context, "Sincronização bem sucedida!");

    }

    //MÉTODO PARA SALVAR DADOS NO FIREBASE
    private static void salvarChamada(ArrayList<Ra> chamada){
        //PUXANDO DATA E HORA ATUAL, PARA USAR COMO CHAVE PRIMARIA
        Date date = new Date();
        String dataAtual = new SimpleDateFormat("ddMMyyyyhhmmss").format(date);

        try{
            databaseReference = ConnectFirebase.getFirebaseDbRef().child("chamadas");//CRIA NÓ (TABELA)
            databaseReference.child(dataAtual).setValue(chamada);//PUXANDO A CHAVE PRIMARIA E INSERINDO USUARIO NOVO NÓ
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //MÉTODO PARA UPDATE DE INFORMAÇÕES DO USUÁRIO
    public static void updateUser(Context context, Professor professor) {
        try{
            //INSERINDO URL DA IMAGEM DE PERFIL NA VARIAVEL ESTATICA
            professor.setUrl(ManipulaArquivo.lerArquivo("linkImgPerfil.txt", context));

            //INSERINDO INFORMAÇÕES DO OBJETO EM UM MAP
            Map<String, Object> prof = professor.toMap();

            //FAZENDO UPDATE DE DAOS
            databaseReference.child("professor").child(auth.getCurrentUser().getUid())
                    .updateChildren(prof);

            //APAGANDO O ARQUIVO DA PASTA
            ManipulaArquivo.apagarArquivo(context, "linkImgPerfil.txt");

        }
        catch(Exception e){
            NotificaUser.alertaToast(context, "Erro ao Gravar Usuário!");
            e.printStackTrace();
        }
    }
}
