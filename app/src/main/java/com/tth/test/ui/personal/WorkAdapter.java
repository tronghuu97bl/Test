package com.tth.test.ui.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tth.test.R;

import java.util.ArrayList;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private Context context;
    private ArrayList work_title, work_last_modify;

    WorkAdapter(ArrayList work_title,  ArrayList work_last_modify) {
        this.work_title = work_title;
        this.work_last_modify = work_last_modify;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View workView = inflater.inflate(R.layout.item_note, parent,false);
        ViewHolder viewHolder = new ViewHolder(workView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.editText.setText(String.valueOf(work_last_modify.get(position)));
        holder.checkBox.setText(String.valueOf(work_title.get(position)));
    }

    @Override
    public int getItemCount() {
        return work_title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public EditText editText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            editText=itemView.findViewById(R.id.textView);
        }
    }
}
