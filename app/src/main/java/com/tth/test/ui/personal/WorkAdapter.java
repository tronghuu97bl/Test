package com.tth.test.ui.personal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Work;
import com.tth.test.util.KeyboardUtils;
import com.tth.test.util.RangeTimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private Context context;
    private List<Work> work;
    DBHelper dbHelper;
    String time = "";
    int index_1 = -1;

    WorkAdapter(Context context, List<Work> work) {
        this.work = work;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View workView = inflater.inflate(R.layout.item_work, parent, false);
        ViewHolder viewHolder = new ViewHolder(workView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Work works = work.get(position);
        int checked = works.getChecked();
        holder.textView_ct.setText(works.getContent());
        holder.textView_time.setText(works.getLast_mdf());
        if (checked == 1) {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
            holder.textView_ct.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView_ct.setTextColor(Color.GRAY);
            holder.checkBox.setChecked(true);
            if (index_1 == -1) {
                index_1 = position;
            }
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.textView_ct.setPaintFlags(0);
            //holder.textView_ct.setPaintFlags(holder.textView_ct.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView_ct.setTextColor(Color.BLACK);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(getContext());
                int checked = works.getChecked();
                if (checked == 1) {
                    index_1++;
                    works.setChecked(0);
                    work.remove(works);
                    notifyItemRemoved(position);
                    work.add(0, works);
                    notifyItemInserted(0);
                    //Toast.makeText(getContext(), String.valueOf(index_1), Toast.LENGTH_SHORT).show();
                } else {
                    works.setChecked(1);
                    work.remove(works);
                    notifyItemRemoved(position);
                    if (index_1 == -1) {
                        work.add(work.size(), works);
                        index_1=work.size();
                    } else {
                        index_1--;
                        work.add(index_1 - 1, works);
                    }
                    notifyItemInserted(index_1 - 1);
                    //Toast.makeText(getContext(), String.valueOf(index_1), Toast.LENGTH_SHORT).show();
                    //notifyItemChanged(position);
                }
                dbHelper.updateWork(works);
                dbHelper.close();
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpWindowEdit(view, position);
            }
        });
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

    public void addwork(Work work1) {
        dbHelper = new DBHelper(getContext());
        dbHelper.addWork(work1);
        work.add(0, work1);
        notifyItemInserted(0);
        index_1++;
        //Toast.makeText(context, String.valueOf(index_1), Toast.LENGTH_SHORT).show();
        //workAdapter.notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        DBHelper dbHelper = new DBHelper(getContext());
        Work wo = work.get(position);
        work.remove(wo);
        index_1--;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView textView_ct;
        public TextView textView_time;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_work);
            checkBox = itemView.findViewById(R.id.checkBox_work);
            textView_ct = itemView.findViewById(R.id.textView_content);
            textView_time = itemView.findViewById(R.id.textView_time);
        }
    }

    public void showPopUpWindowEdit(final View view, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_add_work, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 70);
        final TextView test = popupView.findViewById(R.id.editTextTextMultiLine);
        //get work
        final Work wo = work.get(position);
        test.setText(wo.getContent());
        final String last = wo.getLast_mdf();
        //LAM MO NEN
        View container = popupWindow.getContentView().getRootView();
        if (container != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.3f;
            if (wm != null) {
                wm.updateViewLayout(container, p);
            }
        }
        final Button button = popupView.findViewById(R.id.button_settime);
        final Button button2 = popupView.findViewById(R.id.button_hoantat);
        if (last.equals("") == true) {
            button.setText("Đặt nhắc nhở");
        } else {
            button.setText(last);
        }
        button2.setText("Hoàn tất");
        button2.setTextColor(Color.GREEN);
        test.addTextChangedListener(new TextWatcher() {
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
        if (test.getText() == "") button2.setEnabled(false);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wo.setContent(test.getText().toString());
                if (time.equals("") == false) {
                    wo.setLast_mdf(time);
                }
                dbHelper = new DBHelper(getContext());
                dbHelper.updateWork(wo);
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
        KeyboardUtils.showKeyboard2(test);
    }

    private void datePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formatDate = String.format("%d/%d/%d", dayOfMonth, month + 1, year);
                timePicker(formatDate);
            }
        }, year, month, dayOfMonth);
        calendar.add(Calendar.MONTH, 1);
        long now = System.currentTimeMillis() - 1000;
        long maxDate = calendar.getTimeInMillis();
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.getDatePicker().setMaxDate(maxDate); //After one month from now
        datePickerDialog.show();
    }

    private void timePicker(final String date) {
        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String formatTime = String.format("%d:%d", hourOfDay, minute);
                time = date + " " + formatTime;
            }
        }, hour + 1, minute, false);
        timePickerDialog.setMin(hour + 1, minute);
        timePickerDialog.show();
    }
}
