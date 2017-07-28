package com.appnative.dlpires.palmphone_n;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by dlpires on 26/07/17.
 */

public class login_page extends Activity{
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.login_page);
    }

    public void verificaLogin(View v){
        Intent i = new Intent(this, menu_page.class);
        startActivity(i);
    }
}
