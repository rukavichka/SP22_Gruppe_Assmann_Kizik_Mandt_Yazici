package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RefusedLoginActivity extends AppCompatActivity {

    private Button buttonTryAgain;
    private EditText editTextRefusedLogin;
    private TextView textViewCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refused_login);

        buttonTryAgain = findViewById(R.id.buttonRefusedLogin);
        editTextRefusedLogin = findViewById(R.id.textFieldRefusedLogin);
        textViewCode = findViewById(R.id.textViewRefusedLoginCode);

        String code = "1337";
        textViewCode.setText(code);

        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      if(editTextRefusedLogin.getText().toString().equals(code)){
                                                          openActivityLogin();
                                                      }
                                                  }
                                              }
        );
    }

    private void openActivityLogin() {
        Intent intent = new Intent(this, LoginActivity.class); //Tenho que mudar ainda
        startActivity(intent);
    }
}