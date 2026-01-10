package com.example.fitness_splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    Button btn;
    FirebaseAuth mAuth;
    EditText etEmail, etPass;
    TextView signupLink, forgotPass;

    @Override
    public void onStart() {
        super.onStart();
        // --- SESSION HANDLING ---
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToHome();
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
        signupLink = findViewById(R.id.signuptxt);
        forgotPass = findViewById(R.id.forgotPasswordtxt);

        // Sign in Logic
        btn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            goToHome();
                        } else {
                            Toast.makeText(login.this, "Error: Invalid Credentials" , Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        forgotPass.setOnClickListener(v -> {
            EditText resetMail = new EditText(v.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password?");
            passwordResetDialog.setMessage("Enter your email to receive reset link.");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Send", (dialog, which) -> {
                String mail = resetMail.getText().toString();
                if(!mail.isEmpty()){
                    mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused ->
                            Toast.makeText(login.this, "Reset link sent to your email.", Toast.LENGTH_SHORT).show()
                    ).addOnFailureListener(e ->
                            Toast.makeText(login.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });

            passwordResetDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            passwordResetDialog.create().show();
        });


        signupLink.setOnClickListener(v -> startActivity(new Intent(login.this, Signup.class)));
    }

    private void goToHome() {
        Intent intent = new Intent(login.this, home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}