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

import com.google.android.material.snackbar.Snackbar;
import com.tth.test.R;
import com.tth.test.db.DBHelper;
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
        holder.textView_ct.setText(works.getContent());
        holder.textView_time.setText(works.getLast_mdf());
    }

    @Override
    public int getItemCount() {
        return work.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void deleteItem(int position) {
        work.remove(position);
        DBHelper dbHelper = new DBHelper(getContext());
        Work wo = dbHelper.getWork(position);
        dbHelper.deleteWork(wo);
        notifyItemRemoved(position);
    }
    /*private void showUndoSnackbar() {
        View view =findViewById(R.id.constraintLayout1);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        mListItems.add(mRecentlyDeletedItemPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView textView_ct;
        public TextView textView_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox_work);
            textView_ct=itemView.findViewById(R.id.textView_content);
            textView_time=itemView.findViewById(R.id.textView_time);
        }
    }
}
