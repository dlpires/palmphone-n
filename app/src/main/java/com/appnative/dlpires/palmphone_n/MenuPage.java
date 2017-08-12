package com.appnative.dlpires.palmphone_n;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by dlpires on 27/07/17.
 */

public class MenuPage extends Activity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.menu_page);
    }

    public void pagePerfil(View v){
        Intent i = new Intent(this, UserPage.class);
        startActivity(i);
    }

    public void pageColetor(View v){
        Intent i = new Intent(this, ColetorPage.class);
        startActivity(i);
    }

    public void botaoLogout(){
        //Saindo do Sistema
        //FirebaseAuth.getInstance().signOut();

        //Voltando a página de Login
        Intent i = new Intent(MenuPage.this, LoginPage.class);
        startActivity(i);
        finish();
    }
}
