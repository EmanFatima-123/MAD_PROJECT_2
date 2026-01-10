package com.example.fitness_splash;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MessageModel> messageList;
    int ITEM_SENT = 1;
    int ITEM_RECEIVE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_received, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(message.timestamp));

        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.msg.setText(message.message);
            viewHolder.time.setText(time);
        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.msg.setText(message.message);
            viewHolder.time.setText(time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (FirebaseAuth.getInstance().getUid().equals(messageList.get(position).senderId)) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public int getItemCount() { return messageList.size(); }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView msg, time;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.senderMsg);
            time = itemView.findViewById(R.id.senderTime);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView msg, time;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.receiverMsg);
            time = itemView.findViewById(R.id.receiverTime);
        }
    }
}
