package com.appnative.dlpires.palmphone_n.Classes;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by root on 11/03/18.
 */

public class HardwareAccess {
    //MÉTODO DE INICIALIZAÇÃO DO SCANNER
    public static void scannerStart(Context a){
        //ATRIBUTO BARCODE
        Activity activity = (Activity) a;

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); //INSERINDO TIPO DE LEITURA
        integrator.setPrompt("Posicione o código de barras sobre a linha vermelha");
        integrator.setCameraId(0); //USAR APENAS A CAMERA TRASEIRA DO DISPOSITIVO.
        integrator.initiateScan();
    }
}
