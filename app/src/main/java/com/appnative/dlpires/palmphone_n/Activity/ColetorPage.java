package com.appnative.dlpires.palmphone_n.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.appnative.dlpires.palmphone_n.R;

/**
 * Created by dlpires on 28/07/17.
 */

//CLASSE JAVA PARA A TELA DE CONFIGURAÇÕES DE CHAMADA DO USUÁRIO DO APP
public class ColetorPage extends AppCompatActivity {

    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b){
        //INICIANDO A TELA
        super.onCreate(b);
        setContentView(R.layout.coletor_page);

        //INICIANDO A TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarColetor);
        setSupportActionBar(toolbar);
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
        Intent i = new Intent(this, ColetorDadosPage.class);
        startActivity(i);
    }


}
