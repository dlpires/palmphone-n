package com.appnative.dlpires.palmphone_n.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.Classes.Ra;
import com.appnative.dlpires.palmphone_n.DAO.FirebaseAuthDAO;
import com.appnative.dlpires.palmphone_n.DAO.ManipulaArquivo;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dlpires on 27/07/17.
 */


//CLASSE JAVA PARA A TELA PRINCIPAL DO APP
public class MenuPage extends AppCompatActivity {


    private Professor professor;

    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b) {
        //INICIANDO A TELA
        super.onCreate(b);
        setContentView(R.layout.menu_page);

        //INICIANDO TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        //INSTANCIANDO OUTROS OBJETOS
        professor = new Professor();
    }

    //MÉTODO BOTÃO DE PERFIL
    public void pagePerfil(View v) {
        Intent i = new Intent(this, UserPage.class);
        startActivity(i);
    }

    //MÉTODO BOTÃO PARA PAGINA DO COLETOR
    public void pageColetor(View v) {
        Intent i = new Intent(this, ColetorPage.class);
        startActivity(i);
    }

    //MÉTODO ACIONADO NO BOTÃO DE LOGOUT
    public void botaoLogout(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmação")
                .setMessage("Deseja sair do sistema?")
                .setPositiveButton("CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuthDAO.signOut(MenuPage.this);
                                //Voltando a página de Login
                                Intent intent = new Intent(MenuPage.this, LoginPage.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                .setNegativeButton("CANCELAR", null)
                .show();

    }

    public void sincronizarFirebase(View v){

        //VERIFICANDO SE EXISTE ARQUIVOS PARA SINCRONIZAR
        if(!ManipulaArquivo.existeArquivo(MenuPage.this)){
            //SE NÃO EXISTIR ARQUIVO, ELE NOTIFICA USUÁRIO E PARA A SINCRONIZAÇÃO
            NotificaUser.alertaCaixaDialogoSimple(MenuPage.this, "Atenção!", "Não há dados para sincronizar!");
            return;
        }

        //CRIANDO ALERTA PARA SINCRONIZAR DADOS NO SERVIDOR
        new AlertDialog.Builder(this)
                .setTitle("Confirmação")
                .setMessage("Deseja sincronizar os dados com o servidor?")
                .setPositiveButton("CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                salvandoFirebase();
                            }
                        })
                .setNegativeButton("CANCELAR", null)
                .show();
    }

    //MÉTODO PARA SINCRONIZAR DADOS NO FIREABSE
    public void salvandoFirebase() {
        professor.createChamada(this, professor.readJson(this));
    }
}
