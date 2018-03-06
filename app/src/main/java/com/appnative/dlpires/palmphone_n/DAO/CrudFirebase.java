package com.appnative.dlpires.palmphone_n.DAO;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.appnative.dlpires.palmphone_n.Activity.UserPage;
import com.appnative.dlpires.palmphone_n.Classes.Disciplina;
import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dlpires on 11/02/2018.
 */

//CLASSE CRUDFIREBASE (PARA AGREGAR OS TIPOS DE ACESSO PARA FAZER PERSISTENCIA DE INFORMAÇÕES DO FIREBASE)
public class CrudFirebase {

    //REFERENCIAS DO FIREBASE
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private ManipulaArquivo arq;


    //CONSTRUTOR CLASSE
    public CrudFirebase(){
        //INICIALIZAÇÃO REFERENCIAS DO FIREBASE
        storageReference = ConnectFirebase.getStorageReference();
        auth = ConnectFirebase.getFirebaseAuth();
        databaseReference = ConnectFirebase.getFirebaseDbRef();
    }

    //MÉTODO PARA ARMAZENAR INFORMAÇÕES DO USUÁRIO LOGADO EM UM ARQUIVO JSON
    public void infUserLogado(final String uid, final Context context){
        //INICIALIZANDO OBJETO RESPONSÁVEL POR MANIPULAÇÃO DE ARQUIVO
        arq = new ManipulaArquivo();
        //FAZENDO ACESSO A BASE DE DADOS DO FIREBASE, BUSCANDO PELO UID DO USUÁRIO AS SUAS INFORMAÇÕES (CHAVE NO DATABASE
        // É IDENTIFICADO PELO USUÁRIO)
        databaseReference.child("professor").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //PEGANDO INFORMAÇÕES DO JSON E INSERINDO NAS CAIXAS DE TEXTO
                arq.gravarArquivo(uid + ".json", dataSnapshot.getValue().toString(), context, dataSnapshot.child("emailProf").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //VERIFICA SE USUÁRIO ESTA LOGADO, PARA MOSTRAR A MENSAGEM DE ERRO APENAS QUANDO O USUÁRIO ESTA LOGADO MAS A CONEXÃO
                //COM O BANCO FOI PERDIDA
                if(usuarioLogado())
                    NotificaUser.alertaToast(context, databaseError.toString());
            }
        });
    }

    //MÉTODO PARA BUSCAR E MOSTRAR IMAGEM DO PERFIL DO USUÁRIO
    public void carregaImagemPerfilUser(final ImageView imageView, final Context context){

        //PEGANDO REFERENCIA DO ARQUIVO QUE SERÁ MOSTRADO, ONDE O NOME DESTE SE BASEIA NO UID DO USUÁRIO QUE FOI CADASTRADO
        StorageReference montarImagemReferencia = storageReference.child("fotoPerfilProfessor/" + auth.getCurrentUser().getUid() + ".jpg");

        //EXECUTANDO MÉTODO DE REFERENCIA PARA BUSCAR OS DADOS NO FIREBASE
        montarImagemReferencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            //MÉTODO QUE EXECUTA A AÇÃO DE QUANDO FOI BEM SUCEDIDA A BUSCA, QUE NO CASO ELE UTILIZADO DA BIBLIOTECA DO PICASSO
            //PARA INSERIR NO IMAGEVIEW A IMAGEM RESGATADA
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri.toString()).resize(200, 200).centerCrop().into(imageView);
            }
            //NO CASO DE FALHA NA CONEXÃO, ELE NOTIFICA AO USUÁRIO DO ERRO ATRAVÉS DE UM TOAST
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                NotificaUser.alertaToast(context, "Falha ao carregar a imagem de perfil!");
            }
        });
    }

    //MÉTODO PARA VERIFICAR SE USUÁRIO JA ESTÁ LOGADO
    public Boolean usuarioLogado(){
        FirebaseUser user = ConnectFirebase.getFirebaseAuth().getCurrentUser();

        if (user != null) return true;
        else return false;
    }
}
