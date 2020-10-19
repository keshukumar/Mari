package com.example.marikiti.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marikiti.R;


public class FragmentBlank extends Fragment {

    public static FragmentBlank newInstance() {
        FragmentBlank fragmentBlank = new FragmentBlank();
        return fragmentBlank;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
