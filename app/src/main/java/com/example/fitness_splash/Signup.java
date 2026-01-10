package com.example.fitness_splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    EditText etName, etEmail, etPass;
    Button btnSignup;
    TextView alreadyHaveAcc;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(Signup.this, home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnSignup = findViewById(R.id.Signupbtn);
        alreadyHaveAcc = findViewById(R.id.alreadyhaveaccounttxt);

        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(Signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // 1. Pehle user ki Unique ID nikalni hai
                            String userId = mAuth.getCurrentUser().getUid();

                            // 2. Database ka reference lena hai (Users naam ka folder banayenge)
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

                            // 3. User ka data aik jagah jama karna hai
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", name);  // Jo naam user ne type kiya
                            userMap.put("email", email); // Jo email user ne type ki
                            userMap.put("uid", userId);  // Firebase ki taraf se mili ID

                            // 4. Database mein save karwana hai
                            mDatabase.child(userId).setValue(userMap).addOnCompleteListener(dbTask -> {
                                if (dbTask.isSuccessful()) {
                                    // Jab database mein save ho jaye, tab Home par bhejna hai
                                    Toast.makeText(Signup.this, "Account Created", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Signup.this, home.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Signup.this, "Database Error: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            // Agar account hi na bane
                            Toast.makeText(Signup.this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        alreadyHaveAcc.setOnClickListener(v -> {
            // Ye line pakka Login screen par le kar jaye gi
            Intent intent = new Intent(Signup.this, login.class);
            startActivity(intent);
            finish(); // Signup screen ko background se khatam karne ke liye
        });
    }
}