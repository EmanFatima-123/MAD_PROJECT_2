package com.example.fitness_splash;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    ArrayList<ChatMessage> list;
    String currentUserId;

    public ChatAdapter(Context context, ArrayList<ChatMessage> list, String currentUserId) {
        this.context = context;
        this.list = list;
        this.currentUserId = currentUserId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage model = list.get(position);

        // Show sender name + message
        holder.senderName.setText(model.getSenderId().equals(currentUserId) ? "You" : "Friend");
        holder.messageText.setText(model.getMessage());

        // Align messages: current user right, other left
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.itemView.getLayoutParams();
        if (model.getSenderId().equals(currentUserId)) {
            params.gravity = Gravity.END;
            holder.itemView.setLayoutParams(params);
            holder.senderName.setGravity(Gravity.END);
            holder.messageText.setGravity(Gravity.END);
        } else {
            params.gravity = Gravity.START;
            holder.itemView.setLayoutParams(params);
            holder.senderName.setGravity(Gravity.START);
            holder.messageText.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView senderName, messageText;

        ViewHolder(View itemView) {
            super(itemView);
            senderName = itemView.findViewById(android.R.id.text1);
            messageText = itemView.findViewById(android.R.id.text2);
        }
    }
}

