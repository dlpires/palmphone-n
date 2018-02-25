package com.appnative.dlpires.palmphone_n.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Ra;
import com.appnative.dlpires.palmphone_n.DAO.ManipulaArquivo;
import com.appnative.dlpires.palmphone_n.DAO.ConnectFirebase;
import com.appnative.dlpires.palmphone_n.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by dlpires on 29/07/17.
 */

//CLASSE JAVA PARA A TELA DE COLETA DE PRESENÇA DO USUÁRIO DO APP
public class ColetorDadosPage extends AppCompatActivity {

    //ATRIBUTOS PARA OS COMPONENTE DA TELA
    private FloatingActionButton buttonBarcode;
    private EditText ra;
    private Button btnFinalizar;
    private Button btnSalvar;
    private Button btnCancelar;


    //REFERENCIAS DE OBJETOS
    private ManipulaArquivo arq;
    private ArrayList<Ra> ras;

    //REFERENCIAS FIREBASE
    private FirebaseAuth auth;

    //OUTRAS REFERENCIAS
    private Date date;

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

        //ATRIBUTOS BOTÕES
        btnSalvar = (Button) findViewById(R.id.buttonRA);
        btnFinalizar = (Button) findViewById(R.id.buttonFinalizar);
        btnCancelar = (Button) findViewById(R.id.buttonCancelar);

        //VÁRIAVEL PARA CAIXA DE TEXTO
        ra = (EditText) findViewById(R.id.textRA);

        //INICIALIZANDO CLASSES E OBJETOS
        arq = new ManipulaArquivo();
        ras = new ArrayList<>();
        date = new Date();

        //INSTANCIA OBJETO FIREBASE
        auth = ConnectFirebase.getFirebaseAuth();

        //EVENTO DE INICIALIZAÇÃO DA LEITURA DE CODIGO DE BARRA E CONFIGURAÇÕES
        buttonBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scannerStart();
            }
        });

        //EVENTO AO CLICAR NO BOTÃO SALVAR
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CRIANDO ALERTA PARA SALVAR RA
                new AlertDialog.Builder(ColetorDadosPage.this)
                        .setTitle("Confirmação")
                        .setMessage("Deseja salvar o RA: " + ra.getText() + "?")
                        .setPositiveButton("CONFIRMAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        salvarRa(ra.getText().toString());
                                        //ALERTA SONORO PARA IDENTIFICAÇÃO DE SALVO
                                        NotificaUser.alertaSonoro(ColetorDadosPage.this);
                                    }
                                })
                        .setNegativeButton("CANCELAR", null)
                        .show();
            }
        });

        //EVENTO DO BOTÃO FINALIZAR CHAMADA
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //VERIFICANDO SE A DADOS PARA FINALIZAR CHAMADA E INFORMANDO AO USUÁRIO
                if(ras.isEmpty()){
                    NotificaUser.alertaCaixaDialogo(ColetorDadosPage.this, "Atenção!", "Sem dados para finalizar a chamada!");
                    return;
                }

                //CRIANDO CONFIRMAÇÃO PARA SALVAR A CHAMADA
                new AlertDialog.Builder(ColetorDadosPage.this)
                        .setTitle("Confirmação")
                        .setMessage("Deseja finalizar a chamada?")
                        .setPositiveButton("CONFIRMAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finalizarChamada();
                                    }
                                })
                        .setNegativeButton("CANCELAR", null)
                        .show();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CRIANDO CONFIRMAÇÃO PARA CANCELAR A CHAMADA
                new AlertDialog.Builder(ColetorDadosPage.this)
                        .setTitle("Confirmação")
                        .setMessage("Deseja cancelar a chamada?")
                        .setPositiveButton("CONFIRMAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        voltarPaginaColetor();
                                    }
                                })
                        .setNegativeButton("CANCELAR", null)
                        .show();
            }
        });
    }

    //MÉTODO PARA FINALIZAR CHAMADA
    private void finalizarChamada() {

        //INICIALIZANDO OBJETO GSON
        Gson gson = new Gson();

        //RESGATANDO EMAIL DO USUÁRIO LOGADO
        String emailUser = auth.getCurrentUser().getEmail().toString();

        //FORMANDO NOME DO ARQUIVO A SER SALVO
        String nomeArquivo = arq.lerArquivo("disciplina.txt", ColetorDadosPage.this, emailUser) + ".json";

        //PEGANDO STRING EM FORMATO JSON
        String json = gson.toJson(ras);

        //SALVANDO O ARQUIVO
        arq.gravarArquivo(nomeArquivo, json, ColetorDadosPage.this, emailUser);

        //NOTIFICANDO O USUÁRIO
        NotificaUser.alertaToast(ColetorDadosPage.this,"Chamada Finalizada!");

        //VOLTANDO A PÁGINA ANTERIOR
        voltarPaginaColetor();
    }

    //MÉTODO PARA CANCELAR CHAMADA
    private void voltarPaginaColetor() {
        Intent i = new Intent(this, ColetorPage.class);
        startActivity(i);
    }

    //MÉTODO DE INICIALIZAÇÃO DO SCANNER
    private void scannerStart(){
        //ATRIBUTO BARCODE
        final Activity activity = this;

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); //INSERINDO TIPO DE LEITURA
        integrator.setCameraId(0); //USAR APENAS A CAMERA TRASEIRA DO DISPOSITIVO.
        integrator.initiateScan();
    }

    //MÉTODO SOBRESCRITO ONDE ANALISA A CAPTURA DO CÓDIGO DE BARRA PELO LEITOR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //OBJETO QUE PEGA O RESULTADO DO LEITOR DE CÓDIGO DE BARRA
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        //ONDE SE HOUVER ALGUM RESULTADO (O CÓDIGO DE BARRA SER IDENTIFICADO)
        //IRÁ PEGAR O CONTEUDO E JOGAR NA EDIT TEXT, E SERÁ NOTIFICADO AO USUÁRIO ATRAVÉS DE UM ALERTA SONORO E UM TOAST
        if(result != null){
            if(result.getContents() != null){
                //ALERTA SONORO PARA INFORMA QUE O RA FOI COLETADO
                NotificaUser.alertaSonoro(ColetorDadosPage.this);

                //CRIANDO ALERTA PARA SALVAR RA
                new AlertDialog.Builder(ColetorDadosPage.this)
                        .setTitle("Confirmação")
                        .setMessage("Deseja salvar o RA: " + result.getContents() + "?")
                        .setPositiveButton("CONFIRMAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //CHAMANDO MÉTODO PARA SALVAR RA NO ARQUIVO JSON
                                        salvarRa(result.getContents());
                                        //INICIALIZANDO SCANNER
                                        scannerStart();
                                    }
                                })
                        .setNegativeButton("CANCELAR", null)
                        .show();
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

    //MÉTODO PARA SALVAR RA NO ARQUIVO JSON
    private void salvarRa(final String codigo){

        //CASO A STRING NÃO TENHA CONTEUDO, RETORNA ALERTA AO USUÁRIO
        if(codigo.isEmpty()){
            NotificaUser.alertaToast(ColetorDadosPage.this, "Informe o RA!");
            return;
        }

        //INSTANCIANDO OBJETO PARA SALVAR NO ARRAY RAS
        Ra r = new Ra();

        //SALVANDO INFORMAÇÕES NO OBJETO
        r.setData(new SimpleDateFormat("dd/MM/yyyy/-hh-mm-ss").format(date));
        r.setRa(codigo);

        //INSERINDO NO ARRAYLIST
        ras.add(r);

        //NOTIFICANDO O USUÁRIO
        NotificaUser.alertaToast(ColetorDadosPage.this, "Salvo com Sucesso!");

        //LIMPANDO CAMPO
        ra.setText("");
    }
}