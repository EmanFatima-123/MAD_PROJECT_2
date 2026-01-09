package com.example.fitness_splash;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class chat extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText etMessage;
    Button btnSend;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    ArrayList<ChatMessage> messageList;
    ChatAdapter adapter;

    String senderId;
    String receiverId;
    String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // UI
        recyclerView = findViewById(R.id.chatRecycler);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        // Firebase
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        senderId = auth.getCurrentUser().getUid();
        receiverId = getIntent().getStringExtra("receiverId");

        chatId = getChatId(senderId, receiverId);

        // DEBUG (pehle run me dekh lena)
        Log.d("CHAT_DEBUG", "Sender: " + senderId);
        Log.d("CHAT_DEBUG", "Receiver: " + receiverId);
        Log.d("CHAT_DEBUG", "ChatId: " + chatId);

        messageList = new ArrayList<>();
        adapter = new ChatAdapter(this, messageList, senderId);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadMessages();

        btnSend.setOnClickListener(v -> sendMessage());
    }

    // 🔑 SAME chatId for both users
    private String getChatId(String u1, String u2) {
        if (u1.compareTo(u2) < 0)
            return u1 + "_" + u2;
        else
            return u2 + "_" + u1;
    }

    private void sendMessage() {
        String msg = etMessage.getText().toString().trim();
        if (msg.isEmpty()) return;

        ChatMessage model = new ChatMessage(
                senderId,
                receiverId,
                msg,
                System.currentTimeMillis()
        );

        firestore.collection("messages")
                .document(chatId)
                .collection("chats")
                .add(model);

        etMessage.setText("");
    }

    private void loadMessages() {
        firestore.collection("messages")
                .document(chatId)
                .collection("chats")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {

                    if (value == null) return;

                    messageList.clear();

                    for (DocumentSnapshot doc : value.getDocuments()) {
                        ChatMessage message = doc.toObject(ChatMessage.class);
                        messageList.add(message);
                    }

                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageList.size() - 1);
                });
    }
}
