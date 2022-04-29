package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WelcomeActivity extends AppCompatActivity {
    private Button change_pswd_btn;
    private EditText new_pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bundle extras = getIntent().getExtras();
        this.setTitle("Welcome back, "+ extras.getString("username") + "!");
        change_pswd_btn = findViewById(R.id.buttonChangePassword);
        new_pswd = findViewById(R.id.newPassword);

        change_pswd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = Database.getInstance();                        // getting Database instance
                Bundle extras = getIntent().getExtras();
                String username = extras.getString("username");
                db.setPassword(username, new_pswd.getText().toString());     // setting new passwort
                new_pswd.setText(R.string.new_password);

                // checking, how change password is working
                // name and new password will be printed
                System.out.println(db.users.get(0).getUsername() + " "+ db.users.get(0).getPassword());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        return true;
    }
}