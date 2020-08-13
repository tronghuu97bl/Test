package com.tth.test.ui.personal;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Note;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> note;
    private Context context;
    DBHelper dbHelper;
    private ArrayAdapter<Note> listViewAdapter;
//    private ItemClickListener mClickListener;
    public NoteAdapter(Context context, List<Note> note){
            this.context = context;
            this.note = note;
            }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item_home, parent,false);
//            int height = parent.getMeasuredHeight()/4;
//            view.setMinimumHeight(height);
            return new ViewHolder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Note notes = note.get(position);
        holder.noteContent.setText(notes.getContent());
        holder.noteTitle.setText(notes.getTitle());
        if(notes.getChecked()==1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Click Long", Toast.LENGTH_SHORT).show();
                AddNote addNote = new AddNote();
                addNote.showPopUpWindow(view);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(context, "Click Long", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.inflate(R.menu.layout_popup_home);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.itemMenu_edit:
                                Toast.makeText(context, notes.getChecked(), Toast.LENGTH_LONG);


                                break;
                            case R.id.itemMenu_done:
                                notes.setChecked(1);
                                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                                break;
                            case R.id.itemMenu_delete:


                                break;
                            case R.id.itemMenu_secure:
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });

        }

    @Override
    public int getItemCount() {
            return note.size();
            }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteContent;
        TextView noteTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteContent = itemView.findViewById(R.id.tv_content);
            noteTitle = itemView.findViewById(R.id.tv_title);

        }

    }
//        @Override
//        public void onClick(View view) {
//            if (mClickListener !=null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//        String getItem(int id){
//        return note[id];
//    }
//        void setClickListener(ItemClickListener itemClickListener){
//            this.mClickListener = itemClickListener;
//        }
//    public interface ItemClickListener{
//        void onItemClick(View view, int position);
}
