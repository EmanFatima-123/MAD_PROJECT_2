package com.example.fitness_splash;

public class UserModel {
    public String uid, name, email;

    // Khali constructor zaroori hai
    public UserModel() {}

    public UserModel(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}

