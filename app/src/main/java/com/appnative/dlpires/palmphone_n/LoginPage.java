package com.appnative.dlpires.palmphone_n;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by dlpires on 26/07/17.
 */

public class LoginPage extends Activity {

    //Variaveis de Email e Senha
    private EditText email;
    private EditText senha;

    /*Variaveis do Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Popup de Loading
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("AUTH", "onAuthStateChanged:signed_out");
                }

            }
        };
    }

    /*Métodos para adicionar e remover o monitoramento do listener*/
    @Override
    public void onStart() {
        super.onStart();
        //Inicializando referencia das caixas de texto login e senha
        this.email = (EditText) findViewById(R.id.textEmail);
        this.senha = (EditText) findViewById(R.id.textSenha);
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //Evento ao botão de Login ser clicado
    public void botaoLogin(View v) {
        verificaLogin(email.getText().toString(), senha.getText().toString());
    }

    /*Método de Verificação de Login*/
    public void verificaLogin(String email, String senha){

        //Autenticação de Login (Email e Senha) no Firebase
        Log.d("AUTH", "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try{
                            Log.d("AUTH", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("AUTH", "signInWithEmail", task.getException());
                                Toast.makeText(LoginPage.this, "Falha na Autenticação.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), MenuPage.class);
                                startActivity(intent);
                                finish();
                            }

                            hideProgressDialog();

                        }catch (Exception e){
                            Log.e("OnComplete Erro", e+"");
                        }


                    }
                });
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
