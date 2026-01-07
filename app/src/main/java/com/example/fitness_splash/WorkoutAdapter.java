package com.example.fitness_splash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter
        extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {


    Context context;

    List<String> workouts;
    int[] images;

    public WorkoutAdapter(Context c, List<String> w, int[] i) {
        context = c;
        workouts = w;
        images = i;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;

        ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgWorkout);
            txt = v.findViewById(R.id.txtWorkout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt.setText(workouts.get(position));
        holder.img.setImageResource(images[position]);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, activity_mode.class);
            i.putExtra("workout_name", workouts.get(position));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }
}

