package com.leotarius.chatspace;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leotarius.chatspace.models.Message;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.AdapterViewHolder> {

    private Context context;
    private ArrayList<Message> messageList;
    private DatabaseReference reference;

    public ChatAdapter(Context context, ArrayList<Message> mList) {
        this.context = context;
        reference = FirebaseDatabase.getInstance().getReference();
        messageList = mList;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        Message message = messageList.get(position);

        holder.name1.setText(message.getName());
        holder.name2.setText(message.getName());
        holder.message1.setText(message.getText());
        holder.message2.setText(message.getText());

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getUid())){
            holder.layout1.setVisibility(View.GONE);
            holder.layout2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout1, layout2;
        TextView name1, name2;
        TextView message1, message2;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            layout1 = itemView.findViewById(R.id.layout1);
            name1 = itemView.findViewById(R.id.name1);
            message1 = itemView.findViewById(R.id.message1);
            layout2 = itemView.findViewById(R.id.layout2);
            name2 = itemView.findViewById(R.id.name2);
            message2 = itemView.findViewById(R.id.message2);
        }
    }
}
