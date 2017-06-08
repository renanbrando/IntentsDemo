package com.gmail.reebrando.intentsdemo.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.TextView;

import com.gmail.reebrando.intentsdemo.R;

public class SMSActivity extends AppCompatActivity {

    private BroadcastReceiver mReceiver;

    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        tvMessage = (TextView) findViewById(R.id.tvMessage);

        if(getIntent() != null) {
            Intent i = getIntent();
            String remetente = i.getStringExtra("remetente");
            String mensagem = i.getStringExtra("mensagem");
            tvMessage.setText(remetente == null && mensagem ==null ? "" : remetente + " : " + mensagem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(
                "android.intent.action.SMSRECEBIDO");

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String remetente = intent.getStringExtra("remetente");
                String mensagem = intent.getStringExtra("mensagem");
                tvMessage.setText(remetente + " : " + mensagem);

            }
        };
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister our receiver
        this.unregisterReceiver(this.mReceiver);
    }

}
