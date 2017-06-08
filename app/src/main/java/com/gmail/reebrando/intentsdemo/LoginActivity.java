package com.gmail.reebrando.intentsdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.onClick;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_USER = 1;
    public final String MY_PREFS = "userdata";

    @BindView(R.id.edUsername)
    EditText edUsername;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        sharedPref = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        //Toast.makeText(this, sharedPref.getString("username", "") + sharedPref.getString("password", ""), Toast.LENGTH_LONG).show();

        if (savedInstanceState != null){
            edUsername.setText(savedInstanceState.getString("username"));
        }

        requestSmsPermission();
    }

    @OnClick(R.id.btnLogin)
    public void newLoginClick(View view){
        if (!edUsername.getText().toString().isEmpty() && !edPassword.getText().toString().isEmpty()){
            if (edUsername.getText().toString().equals(sharedPref.getString("username", "")) &&
                edPassword.getText().toString().equals(sharedPref.getString("password", ""))){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Snackbar.make(view, "Wrong password or username.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        else{
            Snackbar.make(view, "Fill in all fields.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.txtSignUp)
    public void newUserClick(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_NEW_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_NEW_USER:
                edUsername.setText(data.getStringExtra("username"));
                break;
            default:

                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("username", edUsername.getText().toString());
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
}
