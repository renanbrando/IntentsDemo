package com.gmail.reebrando.intentsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by logonrm on 07/06/2017.
 */

public class SMSBroadCastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // é noes
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = intent.getStringExtra("format");
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    } else {
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }

                    String numeroTelefone = currentMessage.getDisplayOriginatingAddress();

                    String mensagem = currentMessage.getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: "+ numeroTelefone + "; message: " + mensagem);

                    Intent i2= new Intent("android.intent.action.SMSRECEBIDO")
                            .putExtra("remetente", numeroTelefone)
                            .putExtra("mensagem", mensagem);
                    context.sendBroadcast(i2);

                    //showNotification(context, numeroTelefone, mensagem);
                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }
}