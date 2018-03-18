package com.appnative.dlpires.palmphone_n.DAO;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * Created by root on 11/03/18.
 */

//CLASSE QUE REALIZA TODOS OS MÉTODOS DE ACESSO PARA O STORAGE DO FAIREBASE
public class FirebaseStorageDAO {

    //ATRIBUTOS NECESSÁRIOS
    private static String string;

    //REFERENCIAS DE OBJETOS
    private static FirebaseStorage storage = ConnectFirebase.getFirebaseStorage();
    private static StorageReference reference = ConnectFirebase.getStorageReference();
    //ADQUIRINDO USUÁRIO
    private static FirebaseAuth auth = ConnectFirebase.getFirebaseAuth();


    //GETTERS E SETTERS
    public static String getString() {
        return string;
    }

    public static void setString(String string) {
        FirebaseStorageDAO.string = string;
    }

    //MÉTODO PARA BUSCAR E MOSTRAR IMAGEM DO PERFIL DO USUÁRIO
    public static void carregaImagemPerfilUser(final Context context, final ImageView imageView){

        //PEGANDO REFERENCIA DO ARQUIVO QUE SERÁ MOSTRADO, ONDE O NOME DESTE SE BASEIA NO UID DO USUÁRIO QUE FOI CADASTRADO
        StorageReference referenciaUser = reference.child("fotoPerfilProfessor/" + auth.getCurrentUser().getUid() + ".jpg");

        //EXECUTANDO MÉTODO DE REFERENCIA PARA BUSCAR OS DADOS NO FIREBASE
        referenciaUser.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            //MÉTODO QUE EXECUTA A AÇÃO DE QUANDO FOI BEM SUCEDIDA A BUSCA, QUE NO CASO ELE UTILIZADO DA BIBLIOTECA DO PICASSO
            //PARA INSERIR NO IMAGEVIEW A IMAGEM RESGATADA
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri.toString()).resize(200, 200).centerCrop().into(imageView);
                //FECHANDO BARRA DE PROGRESSO
                NotificaUser.hideProgressDialog();
            }
            //NO CASO DE FALHA NA CONEXÃO, ELE NOTIFICA AO USUÁRIO DO ERRO ATRAVÉS DE UM TOAST
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //CASO USUÁRIO NÃO POSSUA IMAGEM, SERÁ CARREGADA A IMAGEM PADRÃO
                StorageReference referenciaPadrao = reference.child("fotoPerfilProfessor/naodisponivel.png");

                referenciaPadrao.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                        NotificaUser.alertaToast(context, "Falha ao carregar a imagem!");
                    }
                });

                //FECHANDO BARRA DE PROGRESSO
                NotificaUser.hideProgressDialog();
            }
        });
    }

    //MÉTODO PARA FAZER UPLOAD DA FOTO ESCOLHIDA PELO USUÁRIO
    public static void cadastrarFotoPerfil(final Context context, Uri image) {
        reference = FirebaseStorage.getInstance().getReference();
        StorageReference montarImagemReferencia = reference.child("fotoPerfilProfessor/" + auth.getCurrentUser().getUid() + ".jpg");

        UploadTask uploadTask = montarImagemReferencia.putFile(image);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                NotificaUser.alertaToast(context, "Falha ao Carregar a Imagem");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //SALVANDO URL DA IMAGEM SALVA NO FIREBASE STORAGE
                ManipulaArquivo.gravarArquivo("linkImgPerfil.txt", taskSnapshot.getDownloadUrl().toString(), context);
            }
        });
    }

    //MÉTODO PARA LER URL DO USUÁRIO
    public static void readUrlFotoPerfil(String nomeImagem, final Context context){
        //PEGANDO REFERENCIA DO ARQUIVO QUE SERÁ MOSTRADO, ONDE O NOME DESTE SE BASEIA NO UID DO USUÁRIO QUE FOI CADASTRADO
        StorageReference imagemReferencia = reference.child("fotoPerfilProfessor/" + nomeImagem + ".jpg");

        //EXECUTANDO MÉTODO DE REFERENCIA PARA BUSCAR OS DADOS NO FIREBASE
        imagemReferencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            //MÉTODO QUE EXECUTA A AÇÃO DE QUANDO FOI BEM SUCEDIDA A BUSCA, QUE NO CASO ELE UTILIZADO DA BIBLIOTECA DO PICASSO
            //PARA INSERIR NO IMAGEVIEW A IMAGEM RESGATADA
            @Override
            public void onSuccess(Uri uri) {
                //ADQUIRINDO URL IMAGEM DO USUÁRIO
                setString(uri.toString());
            }
            //NO CASO DE FALHA NA CONEXÃO, ELE NOTIFICA AO USUÁRIO DO ERRO ATRAVÉS DE UM TOAST
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                NotificaUser.alertaToast(context, "Imagem não foi carregada!");
            }
        });

        if(imagemReferencia.getDownloadUrl().isSuccessful()){
            FirebaseStorageDAO.readUrlFotoPerfil("naodisponivel", context);
        }
    }

    //MÉTODO PARA UPDATE DE IMAGEM
    public static void updateImagemPerfilUser(final Context context, final Uri imageUri) {
        reference = FirebaseStorage.getInstance().getReference();
        StorageReference montarImagemReferencia = reference.child("fotoPerfilProfessor/" + auth.getCurrentUser().getUid() + ".jpg");

        montarImagemReferencia.delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //MESMO NA FALHA ELE TBM CADASTRA A IMAGEM(ONDE NÃO FOI ENCONTRADO O ARQUIVO, SENDO UM USUÁRIO COM A IMAGEM PADRÃO)
                cadastrarFotoPerfil(context, imageUri);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cadastrarFotoPerfil(context, imageUri);
            }
        });
    }
}
