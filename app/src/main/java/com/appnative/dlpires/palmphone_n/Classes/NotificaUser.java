package com.appnative.dlpires.palmphone_n.Classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.appnative.dlpires.palmphone_n.R;

import java.util.ArrayList;

/**
 * Created by root on 13/01/18.
 */
//CLASSE NOTIFICAUSER (QUE ARMAZENA TODOS OS TIPOS DE NOTIFICAÇÕES AO USUÁRIO)
public class NotificaUser {

    //Popup de Loading
    private static ProgressDialog mProgressDialog;

    //OUTROS ATRIBUTOS ESTÁTICOS
    private static boolean bool;
    private static boolean [] bList;
    private static ArrayList<String> arrayList = new ArrayList<>();

    //GETTERS E SETTERS
    public static boolean[] getbList() {
        return bList;
    }

    public static void setbList(boolean[] bList) {
        NotificaUser.bList = bList;
    }

    public static ArrayList<String> getArrayList() {
        return arrayList;
    }

    public static void setArrayList(ArrayList<String> arrayList) {
        NotificaUser.arrayList = arrayList;
    }

    public static boolean isBool() {
        return bool;
    }

    public static void setBool(boolean bool) {
        NotificaUser.bool = bool;
    }

    //MÉTODO PARA ALERTA SONORO
    public static void alertaSonoro(Activity a){
        MediaPlayer mediaPlayer = MediaPlayer.create(a, R.raw.zxing_beep);
        mediaPlayer.start();
    }

    //MÉTODO PARA UTILIZAÇÃO DE TOAST
    public static void alertaToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    //MÉTODO PARA INICIAR A BARRA DE PROGRESSO
    public static void showProgressDialog(Context context) {
        mProgressDialog = null;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    //MÉTODO PARA PARAR A BARRA DE PROGRESSO
    public static void hideProgressDialog3000() {
        Handler handler = new Handler();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            //COLOCANDO DELAY DE 3000 MILISEGUNDOS NA BARRA DE PROGRESSO
            handler.postDelayed(new Runnable() {
                public void run() {
                    mProgressDialog.dismiss();
                }
            }, 3000);
        }
    }

    //MÉTODO PARA PARAR A BARRA DE PROGRESSO
    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    //MÉTODO DE CAIXA DE DIALOGO SIMPLES
    public static void alertaCaixaDialogoSimple(Context context, String title, String msg){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }

    //MÉTODO DE CAIXA DE DIALOGO SIMPLES COM EVENTO DE ABRIR ACTIVITIES
    public static void alertaCaixaDialogoOk(final Context context, String title, String msg, final Class c){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, c));
                    }
                })
                .show();
    }
}
