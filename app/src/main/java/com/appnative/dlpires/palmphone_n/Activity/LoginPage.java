package com.appnative.dlpires.palmphone_n.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by dlpires on 26/07/17.
 */

public class LoginPage extends AppCompatActivity {

    //Variaveis de Email e Senha
    private EditText email;
    private EditText senha;

    //Instancia de Objetos
    private Professor professor;

    /*Variavel do Firebase*/
    private FirebaseAuth auth;

    //Popup de Loading
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.login_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);

        //VERIFICA SE USUÁRIO JÁ ESTA LOGADO NO APP
        if (usuarioLogado()){
            Intent intent = new Intent(LoginPage.this, MenuPage.class);
            startActivity(intent);
        }
        else{
            //Resgatando Caixa de Texto
            email = (EditText) findViewById(R.id.textEmail);
            senha = (EditText) findViewById(R.id.textSenha);

        }
    }

    //Evento ao botão de Login ser clicado
    public void botaoLogin(View v) {
        //Instanciando e Inserindo valores no Objeto
        professor = new Professor();
        professor.setEmailProf(email.getText().toString());
        professor.setSenhaProf(senha.getText().toString());

        verificaLogin();
    }

    /*Método de Verificação de Login*/
    public void verificaLogin(){

        //Autenticação de Login (Email e Senha) no Firebase
        Log.d("AUTH", "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        auth = ConnectFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(professor.getEmailProf(), professor.getSenhaProf()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    hideProgressDialog();
                }
                else{
                    Toast.makeText(LoginPage.this, "Falha no Login: Usuario ou Senha Incorreta!", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            }
        });
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginPage.this, MenuPage.class);
        startActivity(intent);
    }

    public Boolean usuarioLogado(){
        FirebaseUser user = ConnectFirebase.getFirebaseAuth().getCurrentUser();

        if (user != null) return true;
        else return false;
    }

    private boolean validateForm() {
        boolean valid = true;

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

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
