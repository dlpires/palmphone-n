package com.appnative.dlpires.palmphone_n.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.Toast;

import com.appnative.dlpires.palmphone_n.R;

/**
 * Created by root on 13/01/18.
 */
//CLASSE NOTIFICAUSER (QUE ARMAZENA TODOS OS TIPOS DE NOTIFICAÇÕES AO USUÁRIO)
public class NotificaUser {

    //Popup de Loading
    private static ProgressDialog mProgressDialog;

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
    public static void hideProgressDialog() {
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
}
