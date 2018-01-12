package com.appnative.dlpires.palmphone_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


/**
 * Created by dlpires on 29/07/17.
 */

public class ColetorDadosPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.coletadados_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarColetaDados);
        setSupportActionBar(toolbar);

    }

    //método para botão voltar da aplicação
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, ColetorPage.class);
                startActivity(intent);
                finish(); // Finaliza a Activity atual
                break;

            default:break;
        }

        return true;
    }

}