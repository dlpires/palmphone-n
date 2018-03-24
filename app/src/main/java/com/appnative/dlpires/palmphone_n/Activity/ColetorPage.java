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
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.DAO.ManipulaArquivo;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by dlpires on 28/07/17.
 */

//CLASSE JAVA PARA A TELA DE CONFIGURAÇÕES DE CHAMADA DO USUÁRIO DO APP
public class ColetorPage extends AppCompatActivity {

    //ATRIBUTOS COMPONENTES
    private EditText nomeUser;
    private Spinner spinnerAula;
    private Spinner spinnerDisciplinas;

    //OUTRAS REFERENCIAS
    private Professor professor;

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

        //INICIALIZANDO OUTROS OBJETOS
        professor = new Professor();

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

        //SALVANDO INFORMAÇÃO DA DISCIPLINA SELECIONADA EM ARQUIVO
        professor.setDisciplinaArq(spinnerDisciplinas.getSelectedItem().toString(),this);

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
    public void carregaInfoUser() {

        carregaSpinnerAula();

        //MÉTODO PARA LER O JSON ARMAZENADO INTERNAMENTE
        Professor prof = professor.readJson(this);

        //INFORMANDO NOME DO PROFESSOR
        nomeUser.setText(prof.getNomeProf());

        ArrayList<String> disc = new ArrayList<>();
        //ADICIONANDO HEADER NO SPINNER
        disc.add("Selecione uma Disciplina:");
        disc.addAll(prof.getDisciplinas());
        //PREENCHENDO SPINNER DE DISCIPLINAS
        preencherSpinner(spinnerDisciplinas, disc);
    }

    //MÉTODO PARA CARREGAR O SPINNER
    private void carregaSpinnerAula() {
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

    //MÉTODO DO BOTÃO VOLTAR DO DISPOSITIVO
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuPage.class);
        startActivity(intent);
        finish(); // Finaliza a Activity atual
    }

}
