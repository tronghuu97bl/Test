package com.tth.test.ui.personal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Note;
import com.tth.test.model.Work;

import java.util.ArrayList;
import java.util.List;

public class WorkFragment extends Fragment {
    List<Work> work;
    DBHelper dbHelper;
    ImageView empty_imageview;
    TextView no_data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_work, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.rv_work);
        work = new ArrayList<>();
        dbHelper =new DBHelper(getActivity());
        dbHelper.createDefaultWorkIfNeed();
        work=dbHelper.getAllWork();
        //button add
        FloatingActionButton add_button = root.findViewById(R.id.add_button);
        empty_imageview = root.findViewById(R.id.empty_imageview);
        no_data = root.findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddWork addWork = new AddWork();
                addWork.showPopUpWindow(view);
            }
        });

        WorkAdapter workAdapter = new WorkAdapter(getContext(),work);
        recyclerView.setAdapter(workAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        return root;
    }
}
