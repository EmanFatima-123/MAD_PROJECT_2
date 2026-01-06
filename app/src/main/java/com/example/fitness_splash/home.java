package com.example.fitness_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    ListView listViewWorkouts;
    Button btnOpenMain;

    List<String> workouts;

    int[] icons = {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listViewWorkouts = findViewById(R.id.listViewWorkouts);
        btnOpenMain = findViewById(R.id.btnOpenMain);

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

        WorkoutAdapter adapter = new WorkoutAdapter(this, workouts, icons);
        listViewWorkouts.setAdapter(adapter);

        listViewWorkouts.setOnItemClickListener((parent, view, position, id) -> {

            String selectedWorkout = workouts.get(position);

            Intent i = new Intent(home.this, activity_mode.class);
            i.putExtra("workout_name", selectedWorkout);
            startActivity(i);
        });

        btnOpenMain.setOnClickListener(v -> {
            startActivity(new Intent(home.this, MainActivity.class));
   });
}
}