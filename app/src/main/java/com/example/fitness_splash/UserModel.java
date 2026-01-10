package com.example.fitness_splash;

public class UserModel {
    public String uid;
    public String name;
    public String email;

    public UserModel() {} // required for Firestore

    public UserModel(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}

