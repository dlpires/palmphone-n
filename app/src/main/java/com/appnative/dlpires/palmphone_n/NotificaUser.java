package com.appnative.dlpires.palmphone_n;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by root on 13/01/18.
 */

public class NotificaUser {

    public void alertaSonoro(Activity a){
        MediaPlayer mediaPlayer = MediaPlayer.create(a, R.raw.zxing_beep);
        mediaPlayer.start();
    }

    public void alertaToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
