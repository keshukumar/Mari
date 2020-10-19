package com.example.marikiti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marikiti.R;
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.adapter.ViewTraderAdapter;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.model.ViewTrader;

import java.util.ArrayList;
import java.util.List;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class ViewTradersActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;

    private RecyclerView rec_select_trader;
    private TextView tv_no_trader_found;
    private ViewTraderAdapter<ViewTrader> viewTraderAdapter;
    private List<ViewTrader> traderList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_view_trader);
        initToolbar();
        initView();
        initList();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        home = mToolbar.findViewById(R.id.home);
        home.setOnClickListener(this);
        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        tv_no_trader_found = findViewById(R.id.tv_no_trader_found);
        rec_select_trader = findViewById(R.id.rec_select_trader);
        rec_select_trader.setHasFixedSize(true);
        rec_select_trader.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void initList() {
        traderList = new ArrayList<>();
        viewTraderAdapter = new ViewTraderAdapter<>(mContext, traderList);
        viewTraderAdapter.setOnRecyclerListItemClickListener(new OnRecyclerListItemClickListener<ViewTrader>() {
            @Override
            public void onItemClick(View view, View rootView, ViewTrader viewTrader, int position) {
                //Toast.makeText(gContext, "click ", Toast.LENGTH_SHORT).show();
                Intent detail = new Intent(mContext, TraderDetailActivity.class);
                startActivity(detail);

            }
        });
        rec_select_trader.setAdapter(viewTraderAdapter);

        for (int i = 0; i < 20; i++) {
            ViewTrader viewTrader = new ViewTrader();
            viewTrader.setName("Trader Name");
            traderList.add(viewTrader);
            viewTraderAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;
        }

    }
}
