package com.appnative.dlpires.palmphone_n;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by dlpires on 28/07/17.
 */

public class UserPage extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.user_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);
    }
}
