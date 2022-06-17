package com.example.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.Model.VerificationProcess;
import com.example.readdatabase.R;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  {
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
        setSupportActionBar(toolbar);

        ImageView burgerButton = findViewById(R.id.burgerMenu);
        drawer = findViewById(R.id.main_activity);

        ImageView logoutButton = findViewById(R.id.logout_button);

        NavigationView navigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                new HomePageFragment()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.meineVeranstaltungen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                                new ScrollFragment()).commit();
                        break;
                    case R.id.alleVeranstaltungen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                                new LectureSearchPageFragment()).commit();
                        break;
                    case R.id.Settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                                new ScrollTest()).commit();
                        break;
                    case R.id.RaumSuchen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                                new ScrollTest()).commit();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        burgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_activity);
                drawerLayout.open();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificationProcess loginProcess = VerificationProcess.getInstance();
                try {
                    loginProcess.logout(loginProcess.getSid());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}