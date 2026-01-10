package com.example.fitness_splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Signup extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText etName, etEmail, etPass;
    Button btnSignup;
    TextView alreadyHaveAcc;

    @Override
    public void onStart() {
        super.onStart();

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

        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();


        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        btnSignup = findViewById(R.id.Signupbtn);
        alreadyHaveAcc = findViewById(R.id.alreadyhaveaccounttxt);

        btnSignup.setOnClickListener(v -> {
            String name = String.valueOf(etName.getText());
            String email = String.valueOf(etEmail.getText());
            String password = String.valueOf(etPass.getText());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {

                Toast.makeText(Signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(Signup.this, "Account Created!.",
                                        Toast.LENGTH_SHORT).show();


                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(Signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });



            Toast.makeText(Signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Signup.this, home.class));
        });

        alreadyHaveAcc.setOnClickListener(v ->
                startActivity(new Intent(Signup.this, login.class)));
    }
}