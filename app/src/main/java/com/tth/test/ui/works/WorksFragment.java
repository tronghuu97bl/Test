package com.tth.test.ui.works;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tth.test.R;

public class WorksFragment extends Fragment {

    private WorksViewModel worksViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        worksViewModel =
                ViewModelProviders.of(this).get(WorksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_works, container, false);
        return root;
    }
}