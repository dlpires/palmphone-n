package com.appnative.dlpires.palmphone_n.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by dlpires on 28/07/17.
 */

public class UserPage extends AppCompatActivity{


    //COMPONENTES TELA
    private ImageView imageView;
    private EditText nome;
    private EditText rg;
    private EditText dtNasc;

    //REFERENCIAS DO FIREBASE
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.user_page);

        //Inicialização da Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);

        //Inicialização Componentes
        imageView = (ImageView) findViewById(R.id.imageUser);
        nome = (EditText) findViewById(R.id.nameUser);
        rg = (EditText) findViewById(R.id.rg);
        dtNasc = (EditText) findViewById(R.id.dtNasc);

        //INICIALIZAÇÃO OBJETOS FIREBASE
        auth = ConnectFirebase.getFirebaseAuth();
        databaseReference = ConnectFirebase.getFirebaseDbRef();

        //MOSTRANDO BARRA DE PROGRESSO (LOADING)
        NotificaUser.showProgressDialog(UserPage.this);
        //MÉTODOS CHAMADOS
        carregaInfoUser();
        carregaImagem();
    }

    private void carregaInfoUser(){

        //PEGANDO EMAIL DO USUARIO LOGADO
        String userLogado = auth.getCurrentUser().getEmail().toString();

        //COMPARANDO COM OS EMAILS CADASTRADOS NO DB
        databaseReference.child("professor").orderByChild("emailProf").equalTo(userLogado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    nome.setText((String) postSnapshot.child("nomeProf").getValue());
                    rg.setText((String) postSnapshot.child("rgProf").getValue());
                    dtNasc.setText((String) postSnapshot.child("dataNascProf").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                NotificaUser.alertaToast(UserPage.this, databaseError.toString());
            }
        });
    }

    private void carregaImagem(){
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference montarImagemReferencia = storageReference.child("fotoPerfilProfessor/" + auth.getCurrentUser().getUid() + ".jpg");

        montarImagemReferencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(UserPage.this).load(uri.toString()).resize(200, 200).centerCrop().into(imageView);
                //FECHANDO BARRA DE PROGRESSO (LOADING)
                NotificaUser.hideProgressDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                NotificaUser.alertaToast(UserPage.this, "Falha ao carregar a imagem de perfil!");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MenuPage.class);
                startActivity(intent);
                finish(); // Finaliza a Activity atual
                break;

            default:break;
        }

        return true;
    }
}
