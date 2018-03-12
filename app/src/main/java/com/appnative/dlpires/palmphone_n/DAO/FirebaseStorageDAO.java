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

    //REFERENCIAS DE OBJETOS
    private static FirebaseStorage storage = ConnectFirebase.getFirebaseStorage();
    private static StorageReference reference = ConnectFirebase.getStorageReference();
    //ADQUIRINDO USUÁRIO
    private static FirebaseAuth auth = ConnectFirebase.getFirebaseAuth();

    //MÉTODO PARA BUSCAR E MOSTRAR IMAGEM DO PERFIL DO USUÁRIO
    public static void carregaImagemPerfilUser(final ImageView imageView, final Context context){

        //PEGANDO REFERENCIA DO ARQUIVO QUE SERÁ MOSTRADO, ONDE O NOME DESTE SE BASEIA NO UID DO USUÁRIO QUE FOI CADASTRADO
        StorageReference referenciaUser = reference.child("fotoPerfilProfessor/" + auth.getCurrentUser().getUid() + ".jpg");

        //CASO USUÁRIO NÃO POSSUA IMAGEM, SERÁ CARREGADA A IMAGEM PADRÃO
        StorageReference referenciaPadrao = reference.child("fotoPerfilProfessor/naodisponivel.png");

        //REFERENCIA UTILIZADA
        StorageReference imagemReferencia;

        //VERIFICANDO SE USUÁRIO POSSUI IMAGEM OU NÃO
        if (referenciaUser.getDownloadUrl().isSuccessful()){
            imagemReferencia = referenciaUser;
        }
        else{
            imagemReferencia = referenciaPadrao;
        }

        //EXECUTANDO MÉTODO DE REFERENCIA PARA BUSCAR OS DADOS NO FIREBASE
        imagemReferencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        //FECHANDO BARRA DE PROGRESSO
        NotificaUser.hideProgressDialog();
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
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    //MÉTODO PARA PEGAR URL DE IMAGEM



}
