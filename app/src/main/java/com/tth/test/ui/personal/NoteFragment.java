package com.tth.test.ui.personal;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tth.test.model.Work;
import com.tth.test.ui.personal.SecureFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Note;
import com.tth.test.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {
    PopupWindow popup;
    List<Note> note;
    DBHelper dbHelper;
    ImageView empty_imageview;
    TextView no_data;
    NoteAdapter noteAdapter;
    ConstraintLayout layout;
    boolean click = true;
    RecyclerView recyclerView = null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        popup = new PopupWindow(getContext());
        layout = new ConstraintLayout(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        //kb note
        note = new ArrayList<>();
        dbHelper = new DBHelper(getActivity());
        dbHelper.createDefaultNotesIfNeed();
        note = dbHelper.getAllNotes();
        int numberOfColumns = 2;
        recyclerView = view.findViewById(R.id.rcv_note);
        recyclerView.setHasFixedSize(true);

        // add btn
        FloatingActionButton add_button = view.findViewById(R.id.add_button);
        FloatingActionButton secure_button = view.findViewById(R.id.secure_button);
        empty_imageview = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpWindow(view);

               
            }
        });
        secure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("He", "click secure");

            }
        });
        noteAdapter = new NoteAdapter(getContext(), note);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new NoteAdapter(getActivity(), note));
        secure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.removeAllViewsInLayout();
                onButtonShowPopupWindowClick(view);
            }
        });
        return view;
    }


    public void addNote(Note note) {
        dbHelper.addNote(note);
    }
    public void onButtonShowPopupWindowClick(View view){

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View popupView = inflater.inflate(R.layout.password_view, null);
        Log.d("Quynh","Test secure button");
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;//lets taps outside the popup also dismiss it
        final  PopupWindow popupWindow = new PopupWindow(popupView, width,height,focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
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
        //show popup window
        //which view you pass in doesn't matter, it is only used for the windeow tolken

        //dissmiss the popup window when touch
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
        //tao bong
    if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
        popupWindow.setElevation(20);
    }
    final TextView password = popupView.findViewById(R.id.edit_password);
    password.setHint("Please enter your password");
    final Button enter = popupView.findViewById(R.id.button_enter);
    enter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (password.getText().toString().equals("123")){
                password.setText("Mat khau chinh xac");
                note.clear();
                note = dbHelper.getAllSecure();
                recyclerView.setAdapter(new NoteAdapter(getActivity(), note));

                Log.d("PassWord","password 6");

            }
            else {
                password.setText("");
                password.setHint("Wrong password");
                password.setHintTextColor(Color.parseColor("#ff7661"));
            }

        }
    });

    }
    public void showPopUpWindow(final View view) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_add_note, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        final TextView title= popupView.findViewById(R.id.edt_titleNote);
        final TextView content = popupView.findViewById(R.id.edt_note);
        //test.setText(R.string.tv_test);
        Button button = popupView.findViewById(R.id.button_time);
        Button button2 = popupView.findViewById(R.id.button_done);
        button.setText("Đặt nhắc nhở");
        button2.setText("Hoàn tất");
        //set time
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "abcdefg", Toast.LENGTH_SHORT).show();
            }
        });
        //save
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(view.getContext());
                Date current_time = Calendar.getInstance().getTime();
                String noteTitle = title.getText().toString();
                String noteContent = content.getText().toString();
                String last_modify = current_time.toString();
                Note note = new Note(noteTitle,noteContent, last_modify,0,0);
                noteAdapter.addnote(note);
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //popupWindow.dismiss();
                return true;
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                KeyboardUtils.showKeyboard2(view);
            }
        });
        KeyboardUtils.showKeyboard2(content);
    }

}
