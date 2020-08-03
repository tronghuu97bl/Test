package com.tth.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tth.test.R;
import com.tth.test.model.Friend;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<Friend> mFriends;

    public FriendAdapter(List<Friend> friends) {
        mFriends = friends;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public Button messageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_name);
            messageButton = itemView.findViewById(R.id.message_button);
        }
    }

    @NonNull
    @Override//to inflate the item layout and create the holder
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View friendView = inflater.inflate(R.layout.item_friend, parent, false);
        ViewHolder viewHolder = new ViewHolder(friendView);
        return viewHolder;
    }

    @Override//chuyen dl vao phan tu
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        //get data model base on position
        Friend friend = mFriends.get(position);
        //set item view based on your view and data model
        TextView textView = holder.nameTextView;
        textView.setText(friend.getName());
        Button button = holder.messageButton;
        button.setText(friend.isOnline()?"Message":"Offline");
        button.setEnabled(friend.isOnline());
    }

    @Override//ho biet so phan tu
    public int getItemCount() {
        return mFriends.size();
    }


}
