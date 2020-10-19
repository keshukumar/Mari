package com.example.marikiti.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.fragment.FragmentSearchTrader;
import com.example.marikiti.fragment.FragmentSelectTrader;
import com.example.marikiti.utilities.NonSwipeViewPager;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private NonSwipeViewPager viewPager;
    private TabLayout tabs;
    private String item;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_shop_detail);
        initToolbar();
        findViewID();
    }


    private void initToolbar()
    {
        Intent intent = getIntent();
        item = intent.getStringExtra("item");

        mToolbar = findViewById(R.id.toolbar);
        title = mToolbar.findViewById(R.id.title);
        title.setText("My Trader");
        try{
            String t=getIntent().getStringExtra("title");
            title.setText(t);
        }catch (NullPointerException e){ title.setText("My Trader");}

        home = mToolbar.findViewById(R.id.home);
        home.setOnClickListener(this);
        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            if (item != null && item.equals("pizza")) {
                home.setImageDrawable(getResources().getDrawable(R.drawable.papa_jo));
                //getSupportActionBar().setLogo(R.drawable.papa_jo);
            } else if (item != null && item.equals("outlet")) {
                home.setImageDrawable(getResources().getDrawable(R.drawable.outlet));
                //getSupportActionBar().setLogo(R.drawable.outlet);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void findViewID()
    {

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPagingEnabled(false);
        SurveyAdapter pagerAdapter = new SurveyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageMargin(10);
        viewPager.setOffscreenPageLimit(1);
        tabs.setupWithViewPager(viewPager);

        View root = tabs.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorWhite));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
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

    private class SurveyAdapter extends FragmentStatePagerAdapter {

        public SurveyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new FragmentSelectTrader();
                    break;
                case 1:
                    fragment = new FragmentSearchTrader();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title="SELECT TRADER";
                    break;
                case 1:
                    title="SEARCH TRADER";
                    break;
            }
            return title;
        }
    }

}
