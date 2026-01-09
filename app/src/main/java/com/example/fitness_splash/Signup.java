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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore; // ✅ Add this
    EditText etName, etEmail, etPass;
    Button btnSignup;
    TextView alreadyHaveAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance(); // ✅ Initialize Firestore

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnSignup = findViewById(R.id.Signupbtn);
        alreadyHaveAcc = findViewById(R.id.alreadyhaveaccounttxt);

        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            String uid = mAuth.getCurrentUser().getUid();

                            HashMap<String, Object> user = new HashMap<>();
                            user.put("uid", uid);
                            user.put("name", name); // username
                            user.put("email", email);

                            firestore.collection("users")  // ✅ Now it will work
                                    .document(uid)
                                    .set(user)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(Signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Signup.this, home.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(Signup.this, "Error saving user", Toast.LENGTH_SHORT).show());

                        } else {
                            Toast.makeText(Signup.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        alreadyHaveAcc.setOnClickListener(v ->
                startActivity(new Intent(Signup.this, login.class)));
    }
}
