package com.tth.test.ui.friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tth.test.R;
import com.tth.test.adapter.FriendAdapter;
import com.tth.test.model.Friend;
import com.tth.test.ui.DetailNoteActivity;

import java.util.ArrayList;

public class FriendFragment extends Fragment {
    ArrayList<Friend> mfFriends;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friend, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);
        RecyclerView recyclerView = root.findViewById(R.id.rv_friend);
        mfFriends = Friend.createContactsList(20);
        FriendAdapter adapter = new FriendAdapter(mfFriends);
        recyclerView.setAdapter(adapter);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        //layoutManager.scrollToPosition(0);
        //StaggeredGridLayoutManager gridLayoutManager =
        //        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getContext(), recyclerView, new RecycleTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "onClick phần tử " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailNoteActivity.class);
                intent.putExtra("ABC","abcdefghijk");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(view.getContext(), "onLongClick phần tử " + position, Toast.LENGTH_SHORT).show();
            }
        }));
        return root;
    }

    public static class RecycleTouchListener implements RecyclerView.OnItemTouchListener {
        public static interface ClickListener {
            public void onClick(View view, int position);

            public void onLongClick(View view, int position);
        }

        private ClickListener clickListener;
        private GestureDetector gestureDetector;

        public RecycleTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}