package com.appnative.dlpires.palmphone_n.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.DAO.ManipulaArquivo;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.DAO.CrudFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dlpires on 28/07/17.
 */

//CLASSE JAVA PARA A TELA DE CONFIGURAÇÕES DE CHAMADA DO USUÁRIO DO APP
public class ColetorPage extends AppCompatActivity {

    //ATRIBUTOS COMPONENTES
    private EditText nomeUser;
    private Spinner spinnerAula;
    private Spinner spinnerDisciplinas;

    //REFERENCIA FIREBASE
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    //REFERENCIA CRUD PARA UTLIZAÇÃO DO FIREBASE
    private CrudFirebase crud;

    //OUTRAS REFERENCIAS
    private ManipulaArquivo arq;

    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b){
        //INICIANDO A TELA
        super.onCreate(b);
        setContentView(R.layout.coletor_page);

        //INICIANDO A TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarColetor);
        setSupportActionBar(toolbar);

        //INCIALIZANDO COMPONENTES DA TELA
        nomeUser = (EditText) findViewById(R.id.nameUser);
        spinnerAula = (Spinner) findViewById(R.id.spinnerAula);
        spinnerDisciplinas = (Spinner) findViewById(R.id.spinnerDisciplina);

        //INICIALIZANDO OBJETOS FIREBASE
        auth = ConnectFirebase.getFirebaseAuth();
        databaseReference = ConnectFirebase.getFirebaseDbRef();

        //INICIALIZANDO OBJETO CRUD
        crud = new CrudFirebase();

        //INICIALIZANDO OBJETOS
        arq = new ManipulaArquivo();

        //INICIANDO PROGRESSBAR
        NotificaUser.showProgressDialog(ColetorPage.this);
        //CHAMANDO MÉTODOS DA CLASSE
        carregaInfoUser();
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
    //MÉTODO ONCLICK DO COLETAR DADOS, ONDE INICIALIZA A ACTIVITY RESPONSÁVEL
    public void pageColeta(View v){
        if(!validateForm()){
            return;
        }
        //SALVANDO DISCIPLINA SELECIONADA NO SPINNER
        arq.gravarArquivo("disciplina.txt", spinnerDisciplinas.getSelectedItem().toString(), ColetorPage.this, auth.getCurrentUser().getEmail().toString());
        Intent i = new Intent(this, ColetorDadosPage.class);
        startActivity(i);
    }

    //MÉTODO PARA VALIDAR O FORM
    private boolean validateForm() {
        if(spinnerDisciplinas.getSelectedItemId() == 0){
            NotificaUser.alertaToast(this, "Selecione a Disciplina");
            return false;
        } else if (spinnerAula.getSelectedItemId() == 0){
            NotificaUser.alertaToast(this, "Selecione o nº de Aulas");
            return false;
        }

        return true;
    }

    //MÉTODO QUE CHAMA AS INFORMAÇÕES DO USUÁRIO
    public void carregaInfoUser(){
        //CRIANDO ARRAY LIST DE NUMERO DE AULAS
        ArrayList<String> nAulas = new ArrayList<String>(){
            {
                add("Selecione o nº de Aulas");
                add("1");
                add("2");
                add("3");
                add("4");
            }
        };
        //CHAMANDO MÉTODO PARA PREENCHER SPINNER
        preencherSpinner(spinnerAula, nAulas);


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
                    nomeUser.setText((String) postSnapshot.child("nomeProf").getValue());

                    //PEGANDO ARRAY COM OS NOMES DAS DISCIPLINAS
                    ArrayList<HashMap<String, String>> disciplinas = (ArrayList<HashMap<String, String>>) postSnapshot.child("disciplinas").getValue();
                    ArrayList<String> nomesDisciplinas = new ArrayList<String>();
                    //ADICIONANDO VALORES DO HASHMAP NO ARRAY DE STRINGS
                    nomesDisciplinas.add("Selecione uma Disciplina");
                    for(int i = 0; i < disciplinas.size(); i++){
                        for(String key : disciplinas.get(i).keySet()){
                            nomesDisciplinas.add(disciplinas.get(i).get(key));
                        }
                    }
                    //CHAMANDO MÉTODO PARA PREENCHER SPINNER
                    preencherSpinner(spinnerDisciplinas, nomesDisciplinas);

                    //FINALIZANDO PROGRESSBAR
                    NotificaUser.hideProgressDialog3000();
                }
            }

            //CASO NÃO FOR ENCONTRADO OU A CONEXÃO FOR CANCELADA, ESTE MÉTODO É ACIONADO
            @Override
            public void onCancelled (DatabaseError databaseError) {
                //VERIFICA SE USUÁRIO ESTA LOGADO, PARA MOSTRAR A MENSAGEM DE ERRO APENAS QUANDO O USUÁRIO ESTA LOGADO MAS A CONEXÃO
                //COM O BANCO FOI PERDIDA
                if(crud.usuarioLogado())
                    NotificaUser.alertaToast(ColetorPage.this, databaseError.toString());
            }
        });
    }

    //MÉTODO PARA PREENCHER SPINNER
    private void preencherSpinner(Spinner spinner, ArrayList<String> arrayList){
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                ColetorPage.this,android.R.layout.simple_spinner_dropdown_item, arrayList) {


            //MÉTODO PARA DESABILITAR O PRIMEIRO ITEM DA LISTA
            @Override
            public boolean isEnabled(int position) {

                if (position == 0) {

                    // Disabilita a primeira posição (hint)
                    return false;

                } else {
                    return true;
                }
            }

            //DEIXANDO O PRIMEIRO ITEM COMO CINZA (HINT)
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {

                    // Deixa o hint com a cor cinza ( efeito de desabilitado)
                    tv.setTextColor(Color.GRAY);

                } else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        //JOGANDO O ARRAY ADAPTER NO SPINNER DA VIEW
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }


}
