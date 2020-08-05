package com.tth.test.ui.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tth.test.R;
import com.tth.test.model.Work;

import java.util.ArrayList;
import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private Context context;
    private List<Work> work;

    WorkAdapter(Context context,List<Work> work) {
        this.work = work;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View workView = inflater.inflate(R.layout.item_work, parent,false);
        ViewHolder viewHolder = new ViewHolder(workView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Work works = work.get(position);
        holder.checkBox.setText(works.getContent());
        holder.textView.setText(works.getLast_mdf());
    }

    @Override
    public int getItemCount() {
        return work.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.cb_work_content);
            textView=itemView.findViewById(R.id.tv_work_mddf);
        }
    }
}
