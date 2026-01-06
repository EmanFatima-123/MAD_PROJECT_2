package com.example.fitness_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class activity_mode extends AppCompatActivity {

    TextView txtMode;
    Button btnRed, btnGreen, btnBlue, btnnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        txtMode = findViewById(R.id.txtMode);
        btnRed  = findViewById(R.id.btnRed);
        btnGreen= findViewById(R.id.btnGreen);
        btnBlue = findViewById(R.id.btnBlue);
        btnnext = findViewById(R.id.btnnext);





        if (savedInstanceState == null) {
            loadFragment(new RedFragment(), false);
        }

        btnRed.setOnClickListener(v -> {
            txtMode.setText("Red fragment");
            loadFragment(new RedFragment(), true);
        });

        btnGreen.setOnClickListener(v -> {
            txtMode.setText("Green fragment");
            loadFragment(new GreenFragment(), true);
        });

        btnBlue.setOnClickListener(v -> {
            txtMode.setText("Blue fragment");
            loadFragment(new BlueFragment(), true);
        });


        btnnext.setOnClickListener(v -> {
            Intent i = new Intent(activity_mode.this, datasending.class);
            startActivity(i);
        });
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        androidx.fragment.app.FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment);

        if (addToBackStack) ft.addToBackStack(null);

        ft.commit();
  }
}