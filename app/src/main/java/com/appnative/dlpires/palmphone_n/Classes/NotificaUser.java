package com.appnative.dlpires.palmphone_n.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.appnative.dlpires.palmphone_n.R;

/**
 * Created by root on 13/01/18.
 */

public class NotificaUser {

    //Popup de Loading
    private static ProgressDialog mProgressDialog;

    public static void alertaSonoro(Activity a){
        MediaPlayer mediaPlayer = MediaPlayer.create(a, R.raw.zxing_beep);
        mediaPlayer.start();
    }

    public static void alertaToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public static  void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
