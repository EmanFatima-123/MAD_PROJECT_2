package com.example.fitness_splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    Button btn;
    FirebaseAuth mAuth;
    EditText  etEmail, etPass;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i1 = new Intent(getApplicationContext(), home.class);
            startActivity(i1);
            finish();
        }
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        btn = findViewById(R.id.Loginbtn);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etpass1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = String.valueOf(etEmail.getText());
                String password = String.valueOf(etPass.getText());

                if ( email.isEmpty() || password.isEmpty()) {

                    Toast.makeText(login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, " login Sucessfully.!",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i1 = new Intent(getApplicationContext(), home.class);
                                    startActivity(i1);
                                    finish();

                                } else {


                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });



            }
        });
    }
}