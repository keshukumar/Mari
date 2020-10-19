package com.example.marikiti.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.MessageActivity;

import gautam.easydevelope.widget.GButton;


public class FragmentMessageNewMessage extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private Context gContext;
    private View view;
    private GButton btn_send;

    public static FragmentMessageNewMessage newInstance() {
        FragmentMessageNewMessage blankFragment = new FragmentMessageNewMessage();
        return blankFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gContext = getActivity();
        view = inflater.inflate(R.layout.fragment_message_new_message, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_send:
                Intent home = new Intent(gContext, MessageActivity.class);
                startActivity(home);
                break;

        }
    }


}