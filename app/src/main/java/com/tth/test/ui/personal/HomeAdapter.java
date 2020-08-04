package com.tth.test.ui.personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tth.test.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends FragmentPagerAdapter {
    private final List<Fragment> listfragmnet = new ArrayList<>();
    private final List<String> title = new ArrayList<>();

    public HomeAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        /*if (position == 0) {
            return PlaceholderFragment.newInstance(position);
        } else {
            return PlaceholderFragment2.newInstance(position);
        }*/
        return listfragmnet.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        /*switch (position) {
            case 1:
                return String.valueOf("Work");
            default:
                return String.valueOf("Note");
        }*/
        return title.get(position);
    }
    public void AddFragment(Fragment fragment, String tit){
        listfragmnet.add(fragment);
        title.add(tit);
    }

    @Override
    public int getCount() {
        return title.size();
    }

    //moi fragment con, tao ra 1 class rieng
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int index) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, index);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_main, container, false);
            return root;
        }
    }

    public static class PlaceholderFragment2 extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment2 newInstance(int index) {
            PlaceholderFragment2 fragment = new PlaceholderFragment2();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, index);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_work, container, false);
            return root;
        }
    }
}
