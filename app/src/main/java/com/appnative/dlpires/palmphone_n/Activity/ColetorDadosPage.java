package com.appnative.dlpires.palmphone_n.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * Created by dlpires on 29/07/17.
 */

//CLASSE JAVA PARA A TELA DE COLETA DE PRESENÇA DO USUÁRIO DO APP
public class ColetorDadosPage extends AppCompatActivity {

    //ATRIBUTOS PARA OS COMPONENTE DA TELA
    private FloatingActionButton buttonBarcode;
    private EditText ra;

    //MÉTODO SOBRESCRITO DA ACTIVITY, PARA INICIALIZAÇÃO DOS COMPONENTES E FUNÇÕES DA TELA
    @Override
    protected void onCreate(Bundle b){
        //INICIALIZAÇÃO DO LAYOUT
        super.onCreate(b);
        setContentView(R.layout.coletadados_page);

        //INICIALIZANDO TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarColetaDados);
        setSupportActionBar(toolbar);

        //ATRIBUTOS BARCODE
        buttonBarcode = (FloatingActionButton) findViewById(R.id.buttonBarcode);
        final Activity activity = this;

        //VÁRIAVEL PARA CAIXA DE TEXTO
        ra = (EditText) findViewById(R.id.textRA);

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

        //PEGA O ID DO ITEM DO MENU DA TOOLBAR E VERIFICA SE É IGUAL ID DO COMPONENTE HOME (VOLTAR), ONDE NO CASO ELE EXECUTA A AÇÃO
        //DE VOLTAR NA PÁGINA ANTERIOR
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

    //MÉTODO SOBRESCRITO ONDE ANALISA A CAPTURA DO CÓDIGO DE BARRA PELO LEITOR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //OBJETO QUE PEGA O RESULTADO DO LEITOR DE CÓDIGO DE BARRA
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        //ONDE SE HOUVER ALGUM RESULTADO (O CÓDIGO DE BARRA SER IDENTIFICADO)
        //IRÁ PEGAR O CONTEUDO E JOGAR NA EDIT TEXT, E SERÁ NOTIFICADO AO USUÁRIO ATRAVÉS DE UM ALERTA SONORO E UM TOAST
        if(result != null){
            if(result.getContents() != null){
                ra.setText(result.getContents());
                NotificaUser.alertaSonoro(this);
                NotificaUser.alertaToast(getApplicationContext(), "Captura bem sucedida!");
            }
            //CASO CPNTRÁRIO, SERÁ INFORMADO AO USUÁRIO QUE A CAPTURA NÃO FOI BEM SUCEDIDA
            else {
                NotificaUser.alertaToast(getApplicationContext(), "Captura não realizada!");
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}