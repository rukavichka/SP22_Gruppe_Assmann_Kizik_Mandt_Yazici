package com.example.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Model.VerificationProcess;
import com.example.readdatabase.R;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this.setTitle(R.string.Login);
        //Database database = Database.getInstance();

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = ((EditText) findViewById(R.id.login_name)).getText().toString();
                String password = ((EditText) findViewById(R.id.login_password)).getText().toString();
                VerificationProcess loginProcess = VerificationProcess.getInstance();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

//                try {
//                    if (loginProcess.login("mriliastest", username, password) == null) {
//                        Toast.makeText(getApplicationContext(), "Username or Password wrong!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        startActivity(intent);
//                    }
//                } catch (ExecutionException | InterruptedException e) {
//                    e.printStackTrace();
//                }
                startActivity(intent);
                if(username.isEmpty()){
                    username = "Default";
                }
                VerificationProcess.getInstance().setUserData(username);
                finish();
            }
        });


    }

}