package com.example.fitness_splash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WorkoutAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> workouts;
    int[] icons;

    public WorkoutAdapter(Context c, List<String> workouts, int[] icons) {
        super(c, R.layout.list_item, workouts);
        this.context = c;
        this.workouts = workouts;
        this.icons = icons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_item, parent, false);

        ImageView icon = row.findViewById(R.id.itemIcon);
        TextView title = row.findViewById(R.id.itemText);

        icon.setImageResource(icons[position]);
        title.setText(workouts.get(position));

        return row;
}
}