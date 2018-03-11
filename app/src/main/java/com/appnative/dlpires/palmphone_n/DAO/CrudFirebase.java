package com.appnative.dlpires.palmphone_n.DAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.appnative.dlpires.palmphone_n.Activity.CadastroPage;
import com.appnative.dlpires.palmphone_n.Activity.LoginPage;
import com.appnative.dlpires.palmphone_n.Activity.MenuPage;
import com.appnative.dlpires.palmphone_n.Activity.UserPage;
import com.appnative.dlpires.palmphone_n.Classes.Disciplina;
import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.ConnectException;
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
    private Professor professor;
    private Gson gson;


    public CrudFirebase(Professor professor){
        //INICIALIZAÇÃO REFERENCIAS DO FIREBASE
        storageReference = ConnectFirebase.getStorageReference();
        auth = ConnectFirebase.getFirebaseAuth();
        databaseReference = ConnectFirebase.getFirebaseDbRef();
        //INICIALIZANDO OBJETO RESPONSÁVEL POR MANIPULAÇÃO DE ARQUIVO
        arq = new ManipulaArquivo();
        this.professor = professor;
    }

    public CrudFirebase(){
        //INICIALIZAÇÃO REFERENCIAS DO FIREBASE
        storageReference = ConnectFirebase.getStorageReference();
        auth = ConnectFirebase.getFirebaseAuth();
        databaseReference = ConnectFirebase.getFirebaseDbRef();
        //INICIALIZANDO OBJETO RESPONSÁVEL POR MANIPULAÇÃO DE ARQUIVO
        arq = new ManipulaArquivo();
    }

    //MÉTODO PARA ARMAZENAR INFORMAÇÕES DO USUÁRIO LOGADO EM UM ARQUIVO JSON
    public void infUserLogado(final String uid, final Context context){
        //INICIALIZANDO BIBLIOTECA GSON
        gson = new Gson();

        //INICIALIZANDO OBJETO RESPONSÁVEL POR MANIPULAÇÃO DE ARQUIVO
        arq = new ManipulaArquivo();
        //FAZENDO ACESSO A BASE DE DADOS DO FIREBASE, BUSCANDO PELO UID DO USUÁRIO AS SUAS INFORMAÇÕES (CHAVE NO DATABASE
        // É IDENTIFICADO PELO USUÁRIO)
        databaseReference.child("professor").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Professor prof = dataSnapshot.getValue(Professor.class);
                //PEGANDO INFORMAÇÕES DO JSON E INSERINDO NAS CAIXAS DE TEXTO
                arq.gravarArquivo(uid + ".json", gson.toJson(prof), context, dataSnapshot.child("emailProf").getValue().toString());
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


    //MÉTODO QUE BUSCA AS DISCIPLINAS DO USUÁRIO E SALVA LOCALMENTE
    public void disciplinasUser(final String uid, final Context context, final String emailUser){
        //FAZENDO ACESSO A BASE DE DADOS DO FIREBASE, BUSCANDO PELO UID DO USUÁRIO AS SUAS INFORMAÇÕES (CHAVE NO DATABASE
        // É IDENTIFICADO PELO USUÁRIO)
        databaseReference.child("professor").child(uid).child("disciplinas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //PEGANDO INFORMAÇÕES DO JSON E INSERINDO NAS CAIXAS DE TEXTO
                arq.gravarArquivo(uid + "-disciplinas.json", dataSnapshot.getValue().toString(), context, emailUser);
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

    //MÉTODO PARA CADASTRO DE USUÁRIO
    public void createUser(final Context context, final Uri image){
        //BARRA DE PROGRESSO INICIADAS
        NotificaUser.showProgressDialog(context);

        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(professor.getEmailProf(), professor.getSenhaProf()).
                addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //VERIFICANDO SE O MÉTODO FOI COMPLETADO COM EXITO, PARA REALIZAR AS PRÓXIMAS TAREFAS
                            cadastrarFotoPerfil(context, image);
                            insereUsuario(context);
                            NotificaUser.alertaCaixaDialogo(context, "Cadastro", "Usuário cadastrado com sucesso!", LoginPage.class);
                        }
                        else{
                            String erroExcecao = null;
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres e com letras e numeros";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Email digitado é invalido! Redigite o campo EMAIL!";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Email já cadastrado!";
                            }catch (Exception e){
                                erroExcecao = "Erro ao cadastrar!";
                                e.printStackTrace();
                            }

                            NotificaUser.alertaToast(context, "Erro: " + erroExcecao);
                        }

                        //BARRA DE PROGRESSO FECHADA
                        NotificaUser.hideProgressDialog();
                    }
                });


    }

    //MÉTODO PARA INSERIR INFORMAÇÕES DO USUÁRIO NO BANCO DE DADOS
    private boolean insereUsuario(Context context){
        try{
            //INSERINDO DADOS NO NÓ PROFESSOR
            databaseReference.child("professor").child(auth.getCurrentUser().getUid())
                    .setValue(professor);//PUXANDO A CHAVE PRIMARIA E INSERINDO USUARIO

            //QUANDO O OBJETO SALVO RETORNAR, SERÁ RETORNADO E FINALIZADA A TAREFA
            if(databaseReference.child("professor").child(auth.getCurrentUser().getUid())
                    .getKey().equals(auth.getCurrentUser().getUid())){
                return true;
            }
        }
        catch(Exception e){
            NotificaUser.alertaToast(context, "Erro ao Gravar Usuário!");
            e.printStackTrace();
            return false;
        }

        return false;
    }

    private void cadastrarFotoPerfil(final Context context, Uri image) {
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference montarImagemReferencia = storageReference.child("fotoPerfilProfessor/" + auth.getCurrentUser().getUid() + ".jpg");

        UploadTask uploadTask = montarImagemReferencia.putFile(image);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Falha ao Carregar a Imagem", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                professor.setUrl(taskSnapshot.getDownloadUrl().toString());
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    //MÉTODO PARA SAIR DO SISTEMA
    public void signOut(Context context){
        //APAGANDO ARQUIVO DE USUÁRIO LOGADO
        arq.apagarArquivo(context, auth.getCurrentUser().getEmail(), auth.getCurrentUser().getUid());

        //Saindo do Sistema
        auth.signOut();
    }
}
