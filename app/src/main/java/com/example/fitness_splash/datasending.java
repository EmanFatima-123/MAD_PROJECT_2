package com.example.fitness_splash;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class datasending extends AppCompatActivity {

    EditText etName;
    DatePicker datePicker;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasending);

        etName = findViewById(R.id.etName);
        datePicker = findViewById(R.id.datePicker);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {

            String name = etName.getText().toString();
            int year = datePicker.getYear();
            int currentYear = 2025;
            int age = currentYear - year;

            // Send data to userInfo screen
            Intent i = new Intent(datasending.this, savedata.class);
            i.putExtra("username", name);
            i.putExtra("age", age);
            startActivity(i);
   });
}
}