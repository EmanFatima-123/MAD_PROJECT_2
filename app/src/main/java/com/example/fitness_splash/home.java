package com.example.fitness_splash;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    ImageView btnMenu;

    List<String> workouts;
    int[] icons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnMenu = findViewById(R.id.btnMenu);
        recyclerView = findViewById(R.id.recyclerWorkouts);


        MobileAds.initialize(this, initializationStatus -> {});


        loadMyAd();

        workouts = new ArrayList<>();
        workouts.add("Chest Workout");
        workouts.add("Arms Workout");
        workouts.add("Legs Workout");
        workouts.add("Back Workout");
        workouts.add("Shoulders");
        workouts.add("Full Body");
        workouts.add("Yoga");
        workouts.add("Cardio");
        workouts.add("Diet Plan");
        workouts.add("Warm-up Routine");

        icons = new int[]{
                R.drawable.chestlogo,
                R.drawable.armslogo,
                R.drawable.legslogo,
                R.drawable.backlogo,
                R.drawable.shoulderslogo,
                R.drawable.fullbodylogo,
                R.drawable.yogalogo,
                R.drawable.cardiologo,
                R.drawable.deitlogo,
                R.drawable.warmuplogo
        };

        // Menu button click
        btnMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));

        // RecyclerView setup
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        WorkoutAdapter adapter = new WorkoutAdapter(this, workouts, icons);
        recyclerView.setAdapter(adapter);

        // Navigation drawer item click
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_mode) {
                startActivity(new Intent(this, activity_mode.class));
            } else if (id == R.id.nav_user) {
                startActivity(new Intent(this, datasending.class));
            } else if (id == R.id.nav_qr) {
                startActivity(new Intent(this, qrscanner.class));
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut(); // Session khatam
                Intent intent = new Intent(home.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_ai) {
                startActivity(new Intent(this, chatbot.class));
            } else if (id == R.id.nav_chat) {
                Intent intent = new Intent(home.this, chat.class);
                intent.putExtra("receiverId", "OTHER_USER_UID");
                startActivity(intent);
            } else if (id == R.id.nav_ad) {
                if (mInterstitialAd != null) {

                    mInterstitialAd.show(home.this);

                    loadMyAd();
                } else {
                    Toast.makeText(this, "Ad is still loading, please wait...", Toast.LENGTH_SHORT).show();
                    loadMyAd();
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }

    private void loadMyAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        // Test Interstitial Ad ID
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }
}



