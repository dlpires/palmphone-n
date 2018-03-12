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

import com.appnative.dlpires.palmphone_n.Classes.HardwareAccess;
import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Ra;
import com.appnative.dlpires.palmphone_n.DAO.FirebaseAuthDAO;
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

    //OUTRAS REFERENCIAS
    private ArrayList<Ra> ras;

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
        ras = new ArrayList<>();

        //EVENTO DE INICIALIZAÇÃO DA LEITURA DE CODIGO DE BARRA E CONFIGURAÇÕES
        buttonBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HardwareAccess.scannerStart(ColetorDadosPage.this);
            }
        });

        //EVENTO AO CLICAR NO BOTÃO SALVAR
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CRIANDO ALERTA PARA SALVAR RA
                new AlertDialog.Builder(ColetorDadosPage.this)
                        .setTitle("Confirmação")
                        .setMessage("Deseja finalizar a chamada?")
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
                    NotificaUser.alertaCaixaDialogoSimple(ColetorDadosPage.this, "Atenção!", "Sem dados para finalizar a chamada!");
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

        //MÉTODO PARA SALVAR
        new Ra().salvarArrayRa(this, ras);
        //VOLTANDO A PÁGINA ANTERIOR
        voltarPaginaColetor();
    }

    //MÉTODO PARA CANCELAR CHAMADA
    private void voltarPaginaColetor() {
        Intent i = new Intent(this, ColetorPage.class);
        startActivity(i);
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
                new AlertDialog.Builder(this)
                        .setTitle("Confirmação")
                        .setMessage("Deseja salvar o RA: " + result.getContents() + "?")
                        .setPositiveButton("CONFIRMAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //CHAMANDO MÉTODO PARA SALVAR RA NO ARQUIVO JSON
                                        salvarRa(result.getContents());
                                        //INICIALIZANDO SCANNER
                                        HardwareAccess.scannerStart(ColetorDadosPage.this);
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

    //MÉTODO PARA SALVAR RA
    private void salvarRa(final String codigo){
        //CASO A STRING NÃO TENHA CONTEUDO, RETORNA ALERTA AO USUÁRIO
        if(codigo.isEmpty()){
            NotificaUser.alertaToast(ColetorDadosPage.this, "Informe o RA!");
            return;
        }

        //SALVAR CÓDIGO
        ras.add(new Ra().salvarRa(codigo));

        //NOTIFICANDO O USUÁRIO
        NotificaUser.alertaToast(ColetorDadosPage.this, "Salvo com Sucesso!");

        //LIMPANDO CAMPO
        ra.setText("");
    }
}