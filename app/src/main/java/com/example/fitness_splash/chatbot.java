package com.example.fitness_splash;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class chatbot extends AppCompatActivity {

    EditText editMessage;
    TextView txtResponse;
    Button btnSend;


    private static final String API_KEY = "AIzaSyAufnFz3C0NaONDRLBnstqyYLRgE8uqAbI";

    private static final String URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=" + API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        editMessage = findViewById(R.id.editMessage);
        txtResponse = findViewById(R.id.txtResponse);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            String msg = editMessage.getText().toString().trim();
            if (!msg.isEmpty()) {
                sendToGemini(msg);
                editMessage.setText("");
            }
        });
    }

    private void sendToGemini(String userMessage) {

        txtResponse.append("\n\nMe: " + userMessage);

        try {
            JSONObject textPart = new JSONObject();
            textPart.put("text",
                    "You are a fitness assistant. Answer clearly.\nUser: " + userMessage);

            JSONArray parts = new JSONArray();
            parts.put(textPart);

            JSONObject content = new JSONObject();
            content.put("parts", parts);

            JSONArray contents = new JSONArray();
            contents.put(content);

            JSONObject body = new JSONObject();
            body.put("contents", contents);

            RequestBody requestBody = RequestBody.create(
                    body.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            txtResponse.append("\n\nERROR: " + e.getMessage()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (!response.isSuccessful()) {
                        runOnUiThread(() ->
                                txtResponse.append("\n\nHTTP ERROR: " + response.code()));
                        return;
                    }

                    String res = response.body().string();

                    try {
                        JSONObject json = new JSONObject(res);
                        JSONArray candidates = json.getJSONArray("candidates");
                        JSONObject content = candidates.getJSONObject(0)
                                .getJSONObject("content");
                        JSONArray parts = content.getJSONArray("parts");
                        String reply = parts.getJSONObject(0).getString("text");

                        runOnUiThread(() ->
                                txtResponse.append("\n\nAI: " + reply));

                    } catch (Exception e) {
                        runOnUiThread(() ->
                                txtResponse.append("\n\nParse Error"));
                    }
                }
            });

        } catch (Exception e) {
            txtResponse.append("\n\nError building request");
        }
    }
}
