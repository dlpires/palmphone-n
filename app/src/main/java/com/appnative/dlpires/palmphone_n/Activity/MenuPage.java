package com.appnative.dlpires.palmphone_n.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by dlpires on 27/07/17.
 */


//CLASSE JAVA PARA A TELA PRINCIPAL DO APP
public class MenuPage extends AppCompatActivity {

    //ATRIBUTOS PARA O FIREBASE
    private FirebaseAuth auth;

    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b){
        //INICIANDO A TELA
        super.onCreate(b);
        setContentView(R.layout.menu_page);

        //INICIANDO TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        //PUXANDO INSTANCIA UNICA DE AUTENTICAÇÃO DO FIREBASE
        auth = ConnectFirebase.getFirebaseAuth();
    }

    //MÉTODO BOTÃO DE PERFIL
    public void pagePerfil(View v){
        Intent i = new Intent(this, UserPage.class);
        startActivity(i);
    }

    //MÉTODO BOTÃO PARA PAGINA DO COLETOR
    public void pageColetor(View v){
        Intent i = new Intent(this, ColetorPage.class);
        startActivity(i);
    }

    //MÉTODO ACIONADO NO BOTÃO DE LOGOUT
    public void botaoLogout(View v){
        //Saindo do Sistema
        auth.signOut();

        //Voltando a página de Login
        Intent i = new Intent(MenuPage.this, LoginPage.class);
        startActivity(i);
        finish();
    }
}
