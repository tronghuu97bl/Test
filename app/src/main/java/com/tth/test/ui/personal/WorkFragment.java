package com.tth.test.ui.personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Note;
import com.tth.test.model.Work;
import com.tth.test.ui.AddWorkActivity;
import com.tth.test.ui.DetailNoteActivity;
import com.tth.test.ui.friend.FriendFragment;
import com.tth.test.util.KeyboardUtils;
import com.tth.test.util.RangeTimePickerDialog;
import com.tth.test.util.RecyclerViewSwipeListener;
import com.tth.test.util.SwipeController;
import com.tth.test.util.SwipeControllerActions;
import com.tth.test.util.SwipeToDelete;
import com.tth.test.util.TimeManagementImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WorkFragment extends Fragment {
    List<Work> work;
    DBHelper dbHelper;
    ImageView empty_imageview;
    TextView no_data;
    WorkAdapter workAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_work, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.rv_work);
        work = new ArrayList<>();
        dbHelper = new DBHelper(getActivity());
        dbHelper.createDefaultWorkIfNeed();
        work = dbHelper.getAllWork();

        //BUTTON ADD
        FloatingActionButton add_button = root.findViewById(R.id.add_button);
        empty_imageview = root.findViewById(R.id.empty_imageview);
        no_data = root.findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpWindow(view);
            }
        });
        workAdapter = new WorkAdapter(getContext(), work);
        recyclerView.setAdapter(workAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //vuot de xoa
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(workAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //EVEN CLICK ITEM RECYCLE VIEW
        recyclerView.addOnItemTouchListener(new WorkFragment.RecycleTouchListener(getContext(), recyclerView, new WorkFragment.RecycleTouchListener.ClickListener() {
            @Override
            //EDIT WORK
            public void onClick(View view, int position) {
                showPopUpWindowEdit(view, position);
                // Toast.makeText(view.getContext(), "onClick phần tử " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return root;
    }

    public void addwork(Work work1) {
        dbHelper.addWork(work1);
        work.add(work1);
        workAdapter.notifyDataSetChanged();
        //workAdapter.notifyItemInserted(0);
    }
    //POPUP ADD WORK
    public void showPopUpWindow(final View view) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_add_work, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 85);
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

        final TextView test = popupView.findViewById(R.id.editTextTextMultiLine);
        final Button button = popupView.findViewById(R.id.button_settime);
        final Button button2 = popupView.findViewById(R.id.button_hoantat);
        button.setText("Đặt nhắc nhở");
        button2.setText("Hoàn tất");
        test.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().length()>0){
                    button2.setTextColor(Color.GREEN);
                    button2.setEnabled(true);
                }else{
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
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(view.getContext());
                Date currentime = Calendar.getInstance().getTime();
                String content = test.getText().toString();
                String last_modify = currentime.toString();
                Work wo = new Work(content, last_modify, 0);
                addwork(wo);
                popupWindow.dismiss();
            }
        });
        //nhan vao khu vuc cua popup
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //popupWindow.dismiss();
                return true;
            }
        });
        //click out popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                KeyboardUtils.showKeyboard2(view);
            }
        });
        KeyboardUtils.showKeyboard2(test);
    }
    //POPUP EDIT WORK
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
        //LAM MO NEN
        View container = popupWindow.getContentView().getRootView();
        if (container != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            //p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //p.dimAmount = 0.3f;
            if (wm != null) {
                wm.updateViewLayout(container, p);
            }
        }

        final Button button = popupView.findViewById(R.id.button_settime);
        final Button button2 = popupView.findViewById(R.id.button_hoantat);
        button.setText("Đặt nhắc nhở");
        button2.setText("Hoàn tất");
        button2.setTextColor(Color.GREEN);
        test.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().length()>0){
                    button2.setTextColor(Color.GREEN);
                    button2.setEnabled(true);
                }else{
                    button2.setTextColor(Color.GRAY);
                    button2.setEnabled(false);
                }
            }
        });
        //set time
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "abcdefg", Toast.LENGTH_SHORT).show();
            }
        });
        //save
        if (test.getText() == "") button2.setEnabled(false);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wo.setContent(test.getText().toString());
                wo.setLast_mdf(Calendar.getInstance().getTime().toString());
                dbHelper.updateWork(wo);
                workAdapter.notifyItemChanged(position);
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

    //DATE PICKER, TIME PICKER
    private void datePicker(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formatDate = String.format("%d/%d/%d", year, month + 1, dayOfMonth);
                //tvSelectDate.setText(formatDate);
                Toast.makeText(getContext(), formatDate, Toast.LENGTH_SHORT).show();
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

    private void timePicker(final String date){
        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //setDate(String.valueOf(new StringBuilder().append(date).append("/").append(month).append("/").append(year).append("")));

                //String time = getResources().getString(R.string.time_format);
                String formatTime = String.format("%d:%d", hourOfDay, minute);
                String dateTime = date + "   " + formatTime;
                //tvSelectDate.setText(dateTime);
                Toast.makeText(getContext(), dateTime, Toast.LENGTH_SHORT).show();
            }
        }, hour + 1, minute, false);
        timePickerDialog.setMin(hour + 1, minute);
        timePickerDialog.show();
    }
    //click long click
    public static class RecycleTouchListener implements RecyclerView.OnItemTouchListener {
        public static interface ClickListener {
            public void onClick(View view, int position);

            public void onLongClick(View view, int position);
        }

        private WorkFragment.RecycleTouchListener.ClickListener clickListener;
        private GestureDetector gestureDetector;

        public RecycleTouchListener(Context context, final RecyclerView recyclerView, final WorkFragment.RecycleTouchListener.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(), "abce asdasd", Toast.LENGTH_SHORT).show();

        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                this.work.clear();
                DBHelper db = new DBHelper(getContext());
                List<Work> list = db.getAllWork();
                this.work.addAll(list);
                // Notify the data change (To refresh the ListView).
                this.workAdapter.notifyDataSetChanged();
            }
        }
    }
}
