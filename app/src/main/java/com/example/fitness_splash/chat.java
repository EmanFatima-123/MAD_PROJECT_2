package com.example.fitness_splash;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class chat extends AppCompatActivity {

    RecyclerView chatRv;
    EditText msgEdit;
    Button sendBtn;
    ChatAdapter adapter;
    ArrayList<MessageModel> list;
    String senderRoom, receiverRoom;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRv = findViewById(R.id.chatRecyclerView);
        msgEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.sendBtn);



        String receiverId = getIntent().getStringExtra("receiverId");
        String senderId = FirebaseAuth.getInstance().getUid();

        // Unique Chat Room IDs
        senderRoom = senderId + receiverId;
        receiverRoom = receiverId + senderId;

        list = new ArrayList<>();
        adapter = new ChatAdapter(list);
        chatRv.setLayoutManager(new LinearLayoutManager(this));
        chatRv.setAdapter(adapter);

        db = FirebaseDatabase.getInstance().getReference("Chats");

        // --- Messages Receive Karna ---
        db.child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    MessageModel model = ds.getValue(MessageModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
                chatRv.scrollToPosition(list.size() - 1); // Scroll to bottom
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // --- Message Send Karna ---
        sendBtn.setOnClickListener(v -> {
            String messageText = msgEdit.getText().toString();
            if (!messageText.isEmpty()) {
                MessageModel model = new MessageModel(messageText, senderId, System.currentTimeMillis());

                db.child(senderRoom).push().setValue(model).addOnSuccessListener(unused -> {
                    db.child(receiverRoom).push().setValue(model);
                });

                msgEdit.setText(""); // Clear input
            }
        });
    }
}