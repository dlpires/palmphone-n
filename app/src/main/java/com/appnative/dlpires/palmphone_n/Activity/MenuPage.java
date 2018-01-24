package com.appnative.dlpires.palmphone_n.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by dlpires on 27/07/17.
 */

public class MenuPage extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.menu_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        auth = ConnectFirebase.getFirebaseAuth();
    }

    public void pagePerfil(View v){
        Intent i = new Intent(this, UserPage.class);
        startActivity(i);
    }

    public void pageColetor(View v){
        Intent i = new Intent(this, ColetorPage.class);
        startActivity(i);
    }

    public void botaoLogout(View v){
        //Saindo do Sistema
        auth.signOut();

        //Voltando a página de Login
        Intent i = new Intent(MenuPage.this, LoginPage.class);
        startActivity(i);
        finish();
    }
}
