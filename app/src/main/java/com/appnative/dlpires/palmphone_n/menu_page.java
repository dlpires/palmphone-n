package com.appnative.dlpires.palmphone_n;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by dlpires on 27/07/17.
 */

public class menu_page extends Activity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.menu_page);
    }

    public void pagePerfil(View v){
        Intent i = new Intent(this, user_page.class);
        startActivity(i);
    }

    public void pageColetor(View v){
        Intent i = new Intent(this, coletor_page.class);
        startActivity(i);
    }
}
