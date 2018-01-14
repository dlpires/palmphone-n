package com.appnative.dlpires.palmphone_n;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * Created by dlpires on 29/07/17.
 */

public class ColetorDadosPage extends AppCompatActivity {

    private FloatingActionButton buttonBarcode;
    private EditText ra;
    private NotificaUser n;

    @Override
    protected void onCreate(Bundle b){
        //INICIALIZAÇÃO DO LAYOUT
        super.onCreate(b);
        setContentView(R.layout.coletadados_page);

        //INICIALIZANDO TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarColetaDados);
        setSupportActionBar(toolbar);

        //VÁRIAVEIS BARCODE
        buttonBarcode = (FloatingActionButton) findViewById(R.id.buttonBarcode);
        final Activity activity = this;

        //VÁRIAVEL PARA CAIXA DE TEXTO
        ra = (EditText) findViewById(R.id.textRA);

        //PARA AS NOTIFICAÇÕES
        n = new NotificaUser();

        //EVENTO DE INICIALIZAÇÃO DA LEITURA DE CODIGO DE BARRA E CONFIGURAÇÕES
        buttonBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); //INSERINDO TIPO DE LEITURA
                integrator.setCameraId(0); //USAR APENAS A CAMERA TRASEIRA DO DISPOSITIVO.
                integrator.initiateScan();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() != null){
                ra.setText(result.getContents());
                n.alertaSonoro(this);
                n.alertaToast(getApplicationContext(), "Captura bem sucedida!");
            }
            else {
                n.alertaToast(getApplicationContext(), "Captura não realizada!");
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}