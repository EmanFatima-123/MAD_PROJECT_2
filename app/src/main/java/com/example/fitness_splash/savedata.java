package com.example.fitness_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class savedata extends AppCompatActivity {

    TextView tvResult;
    Button btnBack, btnNext; // Add a Next button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedata);

        tvResult = findViewById(R.id.tvResult);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);


        String name = getIntent().getStringExtra("username");
        int age = getIntent().getIntExtra("age", 0);

        tvResult.setText("Username: " + name + "\nAge: " + age);

        Toast.makeText(savedata.this, "Info saved successfully", Toast.LENGTH_SHORT).show();


        btnBack.setOnClickListener(v -> finish());


        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(savedata.this, last.class);
            intent.putExtra("username", name); // pass data forward
            intent.putExtra("age", age);
            startActivity(intent);
   });
}
}