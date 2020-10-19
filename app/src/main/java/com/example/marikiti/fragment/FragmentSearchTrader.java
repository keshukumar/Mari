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
import com.example.marikiti.activity.TheOutlet2Activity;
import com.example.marikiti.activity.TraderDetailActivity;
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.adapter.SearchTraderAdapter;
import com.example.marikiti.model.SearchTrader;

import java.util.ArrayList;
import java.util.List;


public class FragmentSearchTrader extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private Context gContext;
    private View view;

    private RecyclerView rec_search_trader;
    private TextView tv_no_trader_found;
    private SearchTraderAdapter<SearchTrader> searchTraderAdapter;
    private List<SearchTrader> traderList;
    private String item;

    public static FragmentSearchTrader newInstance() {
        FragmentSearchTrader blankFragment = new FragmentSearchTrader();
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
        view = inflater.inflate(R.layout.fragment_search_trader, container, false);
        initView(view);
        initList();
        return view;
    }

    private void initView(View view) {
        Intent intent = getActivity().getIntent();
        item = intent.getStringExtra("item");
        tv_no_trader_found = view.findViewById(R.id.tv_no_trader_found);
        rec_search_trader = view.findViewById(R.id.rec_search_trader);
        rec_search_trader.setHasFixedSize(true);
        rec_search_trader.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    private void initList() {
        traderList = new ArrayList<>();
        searchTraderAdapter = new SearchTraderAdapter<>(gContext, traderList);
        searchTraderAdapter.setOnRecyclerListItemClickListener(new OnRecyclerListItemClickListener<SearchTrader>() {
            @Override
            public void onItemClick(View view, View rootView, SearchTrader selectTrader, int position) {
                //Toast.makeText(gContext, "click ", Toast.LENGTH_SHORT).show();
                if (item != null && item.equals("pizza")) {
                    Intent detail = new Intent(gContext, TraderDetailActivity.class);
                    detail.putExtra("item", "pizza");
                    startActivity(detail);
                } else if (item != null && item.equals("outlet")) {
                    Intent detail = new Intent(gContext, TheOutlet2Activity.class);
                    startActivity(detail);
                } else {
                    Intent detail = new Intent(gContext, TraderDetailActivity.class);
                    startActivity(detail);
                }
            }
        });
        rec_search_trader.setAdapter(searchTraderAdapter);

        for (int i = 0; i < 20; i++) {
            SearchTrader searchTrader = new SearchTrader();
            searchTrader.setName("Trader Name");
            traderList.add(searchTrader);
            searchTraderAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


}