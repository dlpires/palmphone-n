package com.appnative.dlpires.palmphone_n.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.DAO.CrudFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

//CLASSE JAVA PARA A TELA DE PERFIL DO USUÁRIO DO APP
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

    //REFERENCIA DO CRUD FIREBASE
    private CrudFirebase crud;

    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b){
        //INICIANDO A TELA
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

        //INICIALIZANDO OBJETO CRUD
        crud = new CrudFirebase();

        //MÉTODOS CHAMADOS
        carregaInfoUser();
        carregaImagem();
    }


    //MÉTODO QUE CHAMA AS INFORMAÇÕES DO USUÁRIO
    public void carregaInfoUser(){

        //PEGANDO EMAIL DO USUARIO LOGADO
        String userLogado = auth.getCurrentUser().getEmail().toString();

        //INSTANCIANDO VALORES
        //COMPARANDO COM OS EMAILS CADASTRADOS NO DB
        databaseReference.child("professor").orderByChild("emailProf").equalTo(userLogado).addListenerForSingleValueEvent(new ValueEventListener() {

            //MÉTODO SOBRESCRITO QUE RESGATA OS DADOS ENCONTRADOS
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //PEGANDO INFORMAÇÕES DO JSON E INSERINDO NAS CAIXAS DE TEXTO
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    nome.setText((String) postSnapshot.child("nomeProf").getValue());
                    rg.setText((String) postSnapshot.child("rgProf").getValue());
                    dtNasc.setText((String) postSnapshot.child("dataNascProf").getValue());
                }
            }

            //CASO NÃO FOR ENCONTRADO OU A CONEXÃO FOR CANCELADA, ESTE MÉTODO É ACIONADO
            @Override
            public void onCancelled (DatabaseError databaseError) {
                //VERIFICA SE USUÁRIO ESTA LOGADO, PARA MOSTRAR A MENSAGEM DE ERRO APENAS QUANDO O USUÁRIO ESTA LOGADO MAS A CONEXÃO
                //COM O BANCO FOI PERDIDA
                if(crud.usuarioLogado())
                    NotificaUser.alertaToast(UserPage.this, databaseError.toString());
            }
        });
    }

    //MÉTODO PARA BUSCAR E MOSTRAR IMAGEM DO PERFIL DO USUÁRIO
    private void carregaImagem(){
        //MÉTODO DO CRUD PARA CARREGAR IMAGEM DE PERFIL DO USUÁRIO ARMAZENADO NO FIREBASE
        crud.carregaImagemPerfilUser(imageView, UserPage.this);

        //FECHANDO BARRA DE PROGRESSO (LOADING)
        NotificaUser.hideProgressDialog3000();
    }

    //MÉTODO QUE ACIONA O BOTÃO VOLTAR DA TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //PEGA O ID DO ITEM DO MENU DA TOOLBAR E VERIFICA SE É IGUAL ID DO COMPONENTE HOME (VOLTAR), ONDE NO CASO ELE EXECUTA A AÇÃO
        //DE VOLTAR NA PÁGINA ANTERIOR
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
