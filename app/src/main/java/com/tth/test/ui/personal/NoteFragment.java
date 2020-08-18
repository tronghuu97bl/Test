package com.tth.test.ui.personal;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import java.util.List;

import static com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {
    List<Note> note;
    DBHelper dbHelper;
    ImageView empty_imageview;
    TextView no_data;
    NoteAdapter noteAdapter;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        //kb note
        note = new ArrayList<>();
        dbHelper = new DBHelper(getActivity());
        dbHelper.createDefaultNotesIfNeed();
        note = dbHelper.getAllNotes();
        int numberOfColumns = 2;
        RecyclerView recyclerView = view.findViewById(R.id.rcv_note);
        recyclerView.setHasFixedSize(true);
        // add btn
        FloatingActionButton add_button = view.findViewById(R.id.add_button);
        FloatingActionButton secure_button = view.findViewById(R.id.secure_button);
        empty_imageview = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddNote addNote = new AddNote();
                addNote.showPopUpWindow(view);
            }
        });
        secure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("He", "click secure");

            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new NoteAdapter(getActivity(), note));
        return view;
    }
        //click
        //POPUP ADD WORK
//        public void showPopUpWindow(final View view) {
//            LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
//            final View popupView = layoutInflater.inflate(R.layout.activity_add_note, null);
//            int width = LinearLayout.LayoutParams.MATCH_PARENT;
//            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//            boolean focusable = true;
//            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 85);
//            //LAM MO NEN
//            View container = popupWindow.getContentView().getRootView();
//            if (container != null) {
//                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
//                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                p.dimAmount = 0.3f;
//                if (wm != null) {
//                    wm.updateViewLayout(container, p);
//                }
//            }
//
//            final TextView title = popupView.findViewById(R.id.tv_title);
//            final TextView content = popupView.findViewById(R.id.tv_content);
//            final Button button = popupView.findViewById(R.id.button_time);
//            final Button button2 = popupView.findViewById(R.id.button_done);
//            button.setText("Đặt nhắc nhở");
//            button2.setText("Hoàn tất");
//            title.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                    if (time.equals("") == false) {
////                        button.setText(time);
////                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (editable.toString().trim().length() > 0) {
//                        button2.setTextColor(Color.GREEN);
//                        button2.setEnabled(true);
//                    } else {
//                        button2.setTextColor(Color.GRAY);
//                        button2.setEnabled(false);
//                    }
//                }
//            });
//            //set time
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    datePicker();
//                }
//            });
//            //save
//            button2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DBHelper dbHelper = new DBHelper(view.getContext());
//                    String noteContent = content.getText().toString();
//                    String noteTitle = title.getText().toString();
//                    Note no = new Note(noteTitle,noteContent , "anc",0);
//                    //addwork(wo);
//                    dbHelper.addNote(no);
//
//                    //time="";
//                    popupWindow.dismiss();
//                }
//            });
//            //nhan vao khu vuc cua popup
//            popupView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    //popupWindow.dismiss();
//                    return true;
//                }
//            });
//            //click out popup
//            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    KeyboardUtils.showKeyboard2(view);
//                }
//            });
//            KeyboardUtils.showKeyboard2(title);
//        }

    public void addNote(Note note){
       dbHelper.addNote(note);
    }

    }
