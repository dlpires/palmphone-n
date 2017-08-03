package com.appnative.dlpires.palmphone_n;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by dlpires on 28/07/17.
 */

public class ColetorPage extends Activity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.coletor_page);
    }

    public void pageColeta(View v){
        Intent i = new Intent(this, ColetorDadosPage.class);
        startActivity(i);
    }
}
