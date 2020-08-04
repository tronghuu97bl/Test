package com.tth.test.ui.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tth.test.R;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public NoteAdapter(Context context, String[] data){
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_home, parent,false);
            return new ViewHolder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.myTextView.setText(mData[position]);
            }

    @Override
    public int getItemCount() {
            return mData.length;
            }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener !=null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
        String getItem(int id){
            return mData[id];
        }
        void setClickListener(ItemClickListener itemClickListener){
            this.mClickListener = itemClickListener;
        }
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
