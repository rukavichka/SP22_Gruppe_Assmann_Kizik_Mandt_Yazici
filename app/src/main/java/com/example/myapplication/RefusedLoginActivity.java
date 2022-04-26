package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RefusedLoginActivity extends AppCompatActivity {

    private Button buttonRefusedLogin;
    private EditText textFieldRefusedLogin;
    private TextView textViewRefusedLoginCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refused_login);

        buttonRefusedLogin = findViewById(R.id.buttonRefusedLogin);
        textFieldRefusedLogin = findViewById(R.id.textFieldRefusedLogin);
        textViewRefusedLoginCode = findViewById(R.id.textViewRefusedLoginCode);

        String code = "1337";
        textViewRefusedLoginCode.setText(code);

        buttonRefusedLogin.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      if(textFieldRefusedLogin.getText().toString().equals(code)){
                                                          openActivityLogin();
                                                      }
                                                  }
                                              }
        );
    }

    private void openActivityLogin() {
        Intent intent = new Intent(this, MainActivity.class); //Tenho que mudar ainda
        startActivity(intent);
    }
}