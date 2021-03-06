package com.tth.test.ui.works;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tth.test.R;
import com.tth.test.model.Works;

import java.util.List;

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.ViewHolder> {
    private Context context;
    private List<Works> works;

    WorksAdapter(Context context, List<Works> works) {
        this.works = works;
        this.context=context;
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
        Works work = works.get(position);
        holder.checkBox.setText(work.getContent());
        holder.textView.setText(work.getLast_mdf());
    }

    @Override
    public int getItemCount() {
        return works.size();
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
