package com.gmail.reebrando.intentsdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    public final String MY_PREFS = "userdata";

    @BindView(R.id.edUsername)
    EditText edUserName;

    @BindView(R.id.edName)
    EditText edName;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.edRPassword)
    EditText edRPassword;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        sharedPref = getSharedPreferences(MY_PREFS, MODE_PRIVATE);

    }

    @OnClick(R.id.btnCreate)
    public void newUserClick(View view){
        SharedPreferences.Editor editor = sharedPref.edit();
        Intent intent = new Intent();
        if (!edPassword.getText().toString().equals(edRPassword.getText().toString())){
            Snackbar.make(view, "Passwords do not match!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{
            intent.putExtra("username", edUserName.getText().toString());
            editor.putString("username",edUserName.getText().toString());
            editor.putString("password",edPassword.getText().toString());
            editor.apply();
            Toast.makeText(this, "User created successfully.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
