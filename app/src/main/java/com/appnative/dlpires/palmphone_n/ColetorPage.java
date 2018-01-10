package com.appnative.dlpires.palmphone_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by dlpires on 28/07/17.
 */

public class ColetorPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.coletor_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarColetor);
        setSupportActionBar(toolbar);
    }

    public void pageColeta(View v){
        Intent i = new Intent(this, ColetorDadosPage.class);
        startActivity(i);
    }
}
