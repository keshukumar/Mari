package com.example.marikiti.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.MessageDetailActivity;
import com.example.marikiti.adapter.MessageInboxAdapter;
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.model.MessageInbox;

import java.util.ArrayList;
import java.util.List;


public class FragmentMessageInBox extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private Context gContext;
    private View view;

    private RecyclerView rec_message_inbox;
    private TextView tv_no_trader_found;
    private MessageInboxAdapter<MessageInbox> messageInboxAdapter;
    private List<MessageInbox> messageInboxList;

    public static FragmentMessageInBox newInstance() {
        FragmentMessageInBox blankFragment = new FragmentMessageInBox();
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
        view = inflater.inflate(R.layout.fragment_message_inbox, container, false);
        initView(view);
        initList();
        return view;
    }

    private void initView(View view) {
        tv_no_trader_found = view.findViewById(R.id.tv_no_trader_found);
        rec_message_inbox = view.findViewById(R.id.rec_message_inbox);
        rec_message_inbox.setHasFixedSize(true);
        rec_message_inbox.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    private void initList() {
        messageInboxList = new ArrayList<>();
        messageInboxAdapter = new MessageInboxAdapter<>(gContext, messageInboxList);
        messageInboxAdapter.setOnRecyclerListItemClickListener(new OnRecyclerListItemClickListener<MessageInbox>() {
            @Override
            public void onItemClick(View view, View rootView, MessageInbox messageInbox, int position) {
                //Toast.makeText(gContext, "click ", Toast.LENGTH_SHORT).show();
                Intent detail = new Intent(gContext, MessageDetailActivity.class);
                startActivity(detail);
            }
        });
        rec_message_inbox.setAdapter(messageInboxAdapter);

        for (int i = 0; i < 4; i++) {
            MessageInbox messageInbox = new MessageInbox();
            messageInbox.setName("Name");
            messageInboxList.add(messageInbox);
            messageInboxAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


}