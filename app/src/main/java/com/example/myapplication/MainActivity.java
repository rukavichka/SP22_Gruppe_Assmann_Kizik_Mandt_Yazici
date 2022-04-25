package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // checking how change password is working
        Database db = Database.getInstance();
        db.add("Nik", "12345");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // add this to login activity
        login_btn = findViewById(R.id.button2);
        name = findViewById(R.id.editName);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });

    }
}