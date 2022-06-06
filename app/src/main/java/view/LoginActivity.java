package view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readdatabase.R;

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
                Intent intent;
                /**
                if(database.contains(username, password)) {
                    // Change MainActivity.class with successful login page
                    intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    intent.putExtra("username",username);
                }
                else {
                    // Change MainActivity.class with failed login page
                    intent = new Intent(LoginActivity.this, RefusedLoginActivity.class);
                }

                startActivity(intent);
                */
                finish();
            }
        });
    }
}