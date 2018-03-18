package com.appnative.dlpires.palmphone_n.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.DAO.FirebaseAuthDAO;
import com.appnative.dlpires.palmphone_n.DAO.FirebaseDatabaseDAO;
import com.appnative.dlpires.palmphone_n.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by dlpires on 26/07/17.
 */

//CLASSE JAVA PARA A TELA DE LOGIN DO APP

public class LoginPage extends AppCompatActivity {

    //CAIXAS DE TEXTO DO LAYOUT
    private EditText email;
    private EditText senha;

    //REFERENCIA PARA AS INSTANCIAS DE OBJETOS
    private Professor professor;

    //INSTANCIAS FIREBASE
    private FirebaseAuth auth;


    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b){
        //INICIANDO A TELA
        super.onCreate(b);
        setContentView(R.layout.login_page);

        //INSTANCIANDO OBJETO
        professor = new Professor();

        //INSTANCIAS FIREBASE
        auth = ConnectFirebase.getFirebaseAuth();

        //INICIANDO TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);

        //VERIFICA SE USUÁRIO JÁ ESTA LOGADO NO APP, ONDE SE VERDADEIRO, ENTRA NA PAGINA DE MENU DIRETAMENTE, E CASO CONTRARIO ELE INSTANCIA
        //OS EDITTEXT DE LOGIN E SENHA
        if (FirebaseAuthDAO.usuarioLogado()){

            //SALVANDO INFORMAÇÕES DO USUÁRIO LOGADO
            FirebaseDatabaseDAO.infUserLogado(LoginPage.this);
            Intent intent = new Intent(LoginPage.this, MenuPage.class);
            startActivity(intent);
        }
        else{
            //INSTANCIANDO OS OBJETOS COMPONENTES DA CAIXA DE TEXTO
            email = (EditText) findViewById(R.id.textEmail);
            senha = (EditText) findViewById(R.id.textSenha);

        }
    }

    //EVENTO ONCLICK DO BOTÃO LOGIN
    public void botaoLogin(View v) {
        //INSERINDO OS VALORES NO OBJETO.
        professor.setEmailProf(email.getText().toString());
        professor.setSenhaProf(senha.getText().toString());

        //CHAMADA DO MÉTODO DA CLASSE
        verificaLogin();
    }

    //MÉTODO QUE FAZ A AUTENTICAÇÃO DO LOGIN DO USUÁRIO
    public void verificaLogin(){

        //SALVANDO LOG DE LOGIN
        Log.d("AUTH", "signIn:" + email);

        //VERIFICANDO SE AS INFORMAÇÕES CONTIDAS NAS CAIXAS DE TEXTO SÃO VALIDAS
        if (!validateForm()) {
            //RETORNANDO NA TELA DE LOGIN CASO O FORM ESTEJA INCONSISTENTE
            return;
        }

        //MÉTODO PARA LOGIN NO SISTEMA

        //MOSTRANDO BARRA DE PROGRESSO (LOADING)
        NotificaUser.showProgressDialog(this);

        //CHAMANDO MÉTODO DE AUTENTICAÇÃO DO FIREBASE, PASSANDO AS INFORMAÇÕES CONTIDAS NO OBJETO (E RETORNANDO SE FOI REALIZADO OU NÃO)

        auth.signInWithEmailAndPassword(professor.getEmailProf(), professor.getSenhaProf()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //CASO A REQUISIÇÃO FOR VALIDADA, MOSTRAR TELA PRINCIPAL
                if(task.isSuccessful()){
                    //SALVANDO INFORMAÇÕES DO USUÁRIO LOGADO
                    FirebaseDatabaseDAO.infUserLogado(LoginPage.this);
                    abrirTelaPrincipal();
                }
                //CASO CONTRARIO, MOSTRA MENSAGEM AO USUÁRIO
                else{
                    NotificaUser.alertaCaixaDialogoSimple(LoginPage.this,"Falha no Login!" ,"Usuário ou Senha Incorreta!");
                }
                //FECHANDO BARRA DE PROGRESSO (LOADING)
                NotificaUser.hideProgressDialog();
            }
        });

        //ALTERNATIVA NÃO FINALIZADA
        /*if(professor.login(this)){
            abrirTelaPrincipal();
        }*/

    }

    //MÉTODO PARA ABRIR TELA PRINCIPAL
    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginPage.this, MenuPage.class);
        startActivity(intent);
    }

    //MÉTODO PARA ABRIR TELA DE CADASTRO
    public void botaoCadastro(View v){
        Intent intent = new Intent(LoginPage.this, CadastroPage.class);
        startActivity(intent);
    }

    //MÉTODO DE VALIDAÇÃO DO FORM DE LOGIN
    private boolean validateForm() {
        boolean valid = true;

        //VERIFICANDO SE AS CAIXAS DE TEXTO ESTÃO EM BRANCO OU NÃO, DE FORMA A INFORMAR AO USUÁRIO QUE INSIRA AS INFORMAÇÕES
        String userEmail = email.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            email.setError("Required");
            valid = false;
        } else {
            email.setError(null);
        }

        String userPassword = senha.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            senha.setError("Required");
            valid = false;
        } else {
            senha.setError(null);
        }

        return valid;
    }
}
