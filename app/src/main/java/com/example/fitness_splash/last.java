package com.example.fitness_splash;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.google.firebase.auth.FirebaseAuth;

public class last extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    Button btnOpenDrawer;
    Button btnHome, btnMode, btnuserinfo,btnlogout;

    Button btnOpenQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        btnlogout=findViewById(R.id.btnlogout);
        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawerLayout);
        btnOpenDrawer = findViewById(R.id.btnOpenDrawer);
        btnHome = findViewById(R.id.btnHome);
        btnMode = findViewById(R.id.btnMode);
        btnuserinfo = findViewById(R.id.btnUserinfo);
        btnOpenQR = findViewById(R.id.btnOpenQR);

        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));



        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(last.this, home.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });


        btnMode.setOnClickListener(v -> {
            Intent intent = new Intent(last.this, activity_mode.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });


        btnuserinfo.setOnClickListener(v -> {
            Intent intent = new Intent(last.this, datasending.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        btnlogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(last.this, login.class);
            startActivity(intent);

        });

        btnOpenQR.setOnClickListener(v -> {
            Intent intent = new Intent(last.this, qrscanner.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
    }
}