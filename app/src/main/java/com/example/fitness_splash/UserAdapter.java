package com.example.fitness_splash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.VH> {

    Context context;
    ArrayList<UserModel> list;

    public UserAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        UserModel u = list.get(position);
        holder.tv.setText(u.name);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, chat.class);
            i.putExtra("receiverId", u.uid); // ✅ Send real UID
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(View v) {
            super(v);
            tv = v.findViewById(android.R.id.text1);
        }
    }
}
