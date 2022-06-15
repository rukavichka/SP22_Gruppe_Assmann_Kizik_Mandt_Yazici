package com.example.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.readdatabase.R;
import com.example.view.ScrollFragment;
import com.example.view.ScrollTest;
import com.google.android.material.navigation.NavigationView;

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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.meineVeranstaltungen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container, new ScrollFragment()).commit();
                        break;
                    case R.id.alleVeranstaltungen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container, new ScrollTest()).commit();
                        break;

                    case R.id.Settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container, new ScrollTest()).commit();
                        break;

                    case R.id.RaumSuchen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container, new ScrollTest()).commit();
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
    }

}