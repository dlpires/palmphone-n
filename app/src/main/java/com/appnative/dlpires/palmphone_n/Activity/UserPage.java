package com.appnative.dlpires.palmphone_n.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.R;

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
    private FloatingActionButton btnEditar;

    private Professor professor;

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
        btnEditar = (FloatingActionButton) findViewById(R.id.buttonEditar);

        //INICIALIZAÇÃO OBJETOS
        professor = new Professor();

        //MOSTRANDO BARRA DE PROGRESSO (LOADING)
        NotificaUser.showProgressDialog(UserPage.this);

        //MÉTODOS CHAMADOS
        carregaInfoUser();
        carregaImagem();

        //CHAMANDO TELA DE EDITAR
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaEditar();
            }
        });
    }

    //INVOCANDO TELA DE EDITAR PERFIL
    private void telaEditar() {
        Intent intent = new Intent(this, EditarUserPage.class);
        startActivity(intent);
        finish(); // Finaliza a Activity atual
    }


    //MÉTODO QUE CHAMA AS INFORMAÇÕES DO USUÁRIO
    public void carregaInfoUser(){
        Professor prof = professor.readJson(this);

        //CARREGANDO INFORMAÇÕES USER
        nome.setText(prof.getNomeProf());
        rg.setText(prof.getRgProf());
        dtNasc.setText(prof.getDataNascProf());
    }

    //MÉTODO PARA BUSCAR E MOSTRAR IMAGEM DO PERFIL DO USUÁRIO
    private void carregaImagem(){
        //MÉTODO DO CRUD PARA CARREGAR IMAGEM DE PERFIL DO USUÁRIO ARMAZENADO NO FIREBASE
        professor.readImgPerfil(UserPage.this, imageView);
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

    //MÉTODO DO BOTÃO VOLTAR DO DISPOSITIVO
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuPage.class);
        startActivity(intent);
        finish(); // Finaliza a Activity atual
    }
}
