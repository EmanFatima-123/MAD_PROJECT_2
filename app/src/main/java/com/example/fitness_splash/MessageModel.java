package com.example.fitness_splash;

public class MessageModel {
    public String message, senderId;
    public long timestamp;

    public MessageModel() {} // Khali constructor Firebase ke liye

    public MessageModel(String message, String senderId, long timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }
}
