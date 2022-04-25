package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private ImageButton logout_btn;
    private Button change_pswd_btn;
    private EditText new_pswd;
    private TextView welcome_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        logout_btn = findViewById(R.id.imageButton);
        change_pswd_btn = findViewById(R.id.buttonChangePassword);
        new_pswd = findViewById(R.id.newPassword);
        welcome_txt = findViewById(R.id.welcomeText);

        // show name in "Welcome" message
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("username")) {
            String username = extras.getString("username");
            welcome_txt.setText("Welcome back, "+ username + "!");
        }

        // logout Button activity
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        change_pswd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = Database.getInstance();                        // getting Database instance
                Bundle extras = getIntent().getExtras();
                String username = extras.getString("username");
                db.setPassword(username, new_pswd.getText().toString());     // setting new passwort
                new_pswd.setText("New password");

                // checking, how change password is working
                // name and new password will be printed
                System.out.println(db.users.get(0).getUsername() + " "+ db.users.get(0).getPassword());
            }
        });
    }
}