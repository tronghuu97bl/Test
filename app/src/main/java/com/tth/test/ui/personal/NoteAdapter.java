package com.tth.test.ui.personal;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Note;
import com.tth.test.model.Work;
import com.tth.test.util.KeyboardUtils;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

import static com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker;
import static java.security.AccessController.getContext;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> note;
    private Context context;
    DBHelper dbHelper;
    NoteFragment noteFragment ;
    String time = "";
    int index_1 = -1;
    int count =0;
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

        if (notes.getChecked() == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#dce3dc"));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpWindowEdit(view, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                //Toast.makeText(context, "Click Long", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.inflate(R.menu.layout_popup_home);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.itemMenu_done:
                                dbHelper = new DBHelper(context);
                                if (notes.getChecked()==0) {
                                    notes.setChecked(1);
                                    dbHelper.updateNote(notes);
                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    notes.setChecked(0);
                                    dbHelper.updateNote(notes);
                                    notifyDataSetChanged();

                                }


                                break;
                            case R.id.itemMenu_delete:
                                dbHelper = new DBHelper(context);
                                dbHelper.deleteNote(notes);
                                note.remove(notes);
                                //refresh
                                notifyDataSetChanged();
                                break;
                            case R.id.itemMenu_secure:
                                dbHelper = new DBHelper(context);
                                if(notes.getSecure()==0){
                                    notes.setSecure(1);
                                    note.remove(notes);
                                    dbHelper.updateNote(notes);

                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    notes.setSecure(0);
                                    dbHelper.updateNote(notes);
                                    notifyDataSetChanged();
                                }

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
        Button enter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteContent = itemView.findViewById(R.id.tv_content);
            noteTitle = itemView.findViewById(R.id.tv_title);
            enter = itemView.findViewById(R.id.button_enter);

        }

    }
    public void showPopUpWindowEdit(final View view, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_add_note, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 800);
        final TextView title = popupView.findViewById(R.id.edt_titleNote);
        final TextView content =  popupView.findViewById(R.id.edt_note);
        //get work
        final Note no = note.get(position);
        title.setText(no.getTitle());
        content.setText(no.getContent());
        final String last = no.getLast_mdf();
        //LAM MO NEN
        View container = popupWindow.getContentView().getRootView();
        if (container != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.3f;
            if (wm != null) {
                wm.updateViewLayout(container, p);
            }
        }

        final Button button = popupView.findViewById(R.id.button_time);
        final Button button2 = popupView.findViewById(R.id.button_done);
        if (last.equals("") == true) {
            button.setText("Đặt nhắc nhở");
        } else {
            button.setText(last);
        }
        button2.setText("Hoàn tất");
        button2.setTextColor(Color.GREEN);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (last.equals("") == false) {
                    button.setText(last);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    button2.setTextColor(Color.GREEN);
                    button2.setEnabled(true);
                } else {
                    button2.setTextColor(Color.GRAY);
                    button2.setEnabled(false);
                }
            }
        });
        //set time
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
        //save
        if (content.getText() == "") button2.setEnabled(false);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setTitle(title.getText().toString());
                no.setContent(content.getText().toString());
                if (time.equals("") == false) {
                    no.setLast_mdf(time);
                }
                dbHelper = new DBHelper(context);

                dbHelper.updateNote(no);
                notifyItemChanged(position);
                time = "";
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                KeyboardUtils.showKeyboard2(view);
            }
        });
        KeyboardUtils.showKeyboard2(title);
            }
    public void updateList(List<Note> list) {
        note = list;
        notifyDataSetChanged();
    }
    public void addnote(Note note1) {
        dbHelper = new DBHelper(context);
        dbHelper.addNote(note1);
        updateList(note);
    }
    }
