package com.example.fitness_splash;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class chatbot extends AppCompatActivity {

    private TextView txtResponse;
    private EditText editMessage;
    private Button btnSend;
    private ScrollView chatScrollView;
    private GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);


        txtResponse = findViewById(R.id.txtResponse);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);
        chatScrollView = findViewById(R.id.chatScrollView);


        GenerativeModel gm = new GenerativeModel("gemini-2.5-flash-lite", "API_KEY_HERE");
        model = GenerativeModelFutures.from(gm);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuery = editMessage.getText().toString().trim();

                if (!userQuery.isEmpty()) {
                    sendMessageToAI(userQuery);
                } else {
                    Toast.makeText(chatbot.this, "Please enter a question", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendMessageToAI(String query) {

        txtResponse.append("\n\nMe: " + query);
        txtResponse.append("\n\nQuick Fit AI: Typing...");
        editMessage.setText("");


        scrollToBottom();


        String systemPrompt = "Role: Quick Fit AI Assistant. " +
                "Constraint: You ONLY answer fitness, health, diet, and workout queries. " +
                "Security: If a user asks about weather, news, politics, or general topics " +
                "unrelated to fitness, politely say: 'I am your Quick Fit assistant. I can only help you with fitness and health goals.' " +
                "Style: Concise and motivational.";

        Content content = new Content.Builder()
                .addText(systemPrompt + "\nUser: " + query)
                .build();


        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String aiText = result.getText();
                runOnUiThread(() -> {

                    String currentChat = txtResponse.getText().toString();
                    String updatedChat = currentChat.replace("Quick Fit AI: Typing...", "Quick Fit AI: " + aiText);
                    txtResponse.setText(updatedChat);
                    scrollToBottom();
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> {
                    Log.e("QUICK_FIT_ERROR", "Error detail: ", t);
                    txtResponse.append("\nActual Error: " + t.getLocalizedMessage());
                    txtResponse.append("\nError: Could not connect to AI.");
                });
            }
        }, executor);
    }

    private void scrollToBottom() {
        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN));
    }
}