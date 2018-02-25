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
import com.appnative.dlpires.palmphone_n.Classes.Ra;
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

    private ManipulaArquivo arq;
    //ATRIBUTOS PARA O FIREBASE
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b) {
        //INICIANDO A TELA
        super.onCreate(b);
        setContentView(R.layout.menu_page);

        //INICIANDO TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        //PUXANDO INSTANCIA UNICA DE AUTENTICAÇÃO DO FIREBASE
        auth = ConnectFirebase.getFirebaseAuth();
        databaseReference = ConnectFirebase.getFirebaseDbRef();

        //INCIALIZANDO OUTROS OBJETOS
        arq = new ManipulaArquivo();
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
        //Saindo do Sistema
        auth.signOut();

        //Voltando a página de Login
        Intent i = new Intent(MenuPage.this, LoginPage.class);
        startActivity(i);
        finish();
    }

    public void sincronizarFirebase(View v){

        //VERIFICANDO SE EXISTE ARQUIVOS PARA SINCRONIZAR
        if(!arq.existeArquivo(MenuPage.this, auth.getCurrentUser().getEmail().toString())){
            //SE NÃO EXISTIR ARQUIVO, ELE NOTIFICA USUÁRIO E PARA A SINCRONIZAÇÃO
            NotificaUser.alertaCaixaDialogo(MenuPage.this, "Atenção!", "Não há dados para sincronizar!");
            return;
        }

        //CRIANDO ALERTA PARA SINCRONIZAR DADOS NO SERVIDOR
        new AlertDialog.Builder(MenuPage.this)
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
        final Gson gson = new Gson();

        //PEGANDO EMAIL DO USUÁRIO
        final String userLogado = auth.getCurrentUser().getEmail().toString();

        //INSTANCIANDO VALORES
        //COMPARANDO COM OS EMAILS CADASTRADOS NO DB
        databaseReference.child("professor").orderByChild("emailProf").equalTo(userLogado).addListenerForSingleValueEvent(new ValueEventListener() {

            //MÉTODO SOBRESCRITO QUE RESGATA OS DADOS ENCONTRADOS
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //PEGANDO INFORMAÇÕES DO JSON E INSERINDO NAS CAIXAS DE TEXTO
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //PEGANDO ARRAY COM OS NOMES DAS DISCIPLINAS
                    ArrayList<HashMap<String, String>> disciplinas = (ArrayList<HashMap<String, String>>) postSnapshot.child("disciplinas").getValue();

                    //LENDO O ARRAY
                    for (int i = 0; i < disciplinas.size(); i++) {
                        for (String key : disciplinas.get(i).keySet()) {
                            String nomeArquivo = disciplinas.get(i).get(key) + ".json";
                            //BUSCANDO DADOS DOS ARQUIVOS GERADOS
                            //VERIFICANDO SE EXISTE ARQUIVO SALVO COM ESTE NOME
                            if(!arq.lerArquivo(nomeArquivo, MenuPage.this, userLogado).equals("")){
                                //PUXANDO NO ARRAY E SALVANDO O ARQUIVO
                                ArrayList<Ra> chamadas = gson.fromJson(arq.lerArquivo(nomeArquivo, MenuPage.this, userLogado), new TypeToken<List<Ra>>(){}.getType());
                                //SALVANDO NO FIREBASE
                                salvarChamada(chamadas);
                                //APAGANDO O ARQUIVO SALVO
                                arq.apagarArquivo(MenuPage.this, userLogado, nomeArquivo);
                            }
                        }
                    }

                    NotificaUser.alertaToast(MenuPage.this, "Sincronização bem sucedida!");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //GRAVANDO NOS LOGS O ERRO ENCONTRADO
                Log.d("Erro", databaseError.toString());
            }
        });
    }

    //MÉTODO PARA SALVAR DADOS NO FIREBASE
    private void salvarChamada(ArrayList<Ra> chamada){
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
}
