package com.tth.test.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tth.test.R;
import com.tth.test.ui.DetailNoteActivity;

import java.util.ArrayList;

public class WorkFragment extends Fragment {
    ArrayList<String> titile, last_modify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_work, container, false);
        titile = new ArrayList<>();
        last_modify = new ArrayList<>();
        RecyclerView recyclerView = root.findViewById(R.id.rv_work);
        WorkAdapter workAdapter = new WorkAdapter(titile, last_modify);

        final FloatingActionButton add_button = root.findViewById(R.id.add_button);
        ImageView empty_imageview = root.findViewById(R.id.empty_imageview);
        TextView no_data = root.findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "abc", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailNoteActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(workAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        return root;
    }
}
