package com.example.fitness_splash;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<UserModel> userList;
    UserAdapter adapter;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        recyclerView = findViewById(R.id.recyclerUsers);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        userList = new ArrayList<>();
        adapter = new UserAdapter(this, userList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadUsers();
    }

    void loadUsers() {
        firestore.collection("users")
                .get()
                .addOnSuccessListener(query -> {
                    userList.clear();
                    for (DocumentSnapshot doc : query) {
                        // ✅ Exclude current logged-in user
                        if (!doc.getId().equals(auth.getUid())) {
                            userList.add(doc.toObject(UserModel.class));
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}
