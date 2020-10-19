package com.example.marikiti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.marikiti.R;
import com.example.marikiti.adapter.SlidingImage_Adapter;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.dialog.DialogEnterPin;
import com.example.marikiti.model.ImageModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class BuyInternetBundleActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GTextView tv_1gb_activate, tv_500mb_activate, tv_150mb_activate, tv_50mb_activate, tv_15mb_activate,
            tv_7mb_activate;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.drawable.internet1, R.drawable.internet2, R.drawable.internet3};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_buy_internet_bundle);
        initToolbar();
        findViewID();
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

    private void findViewID() {
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        tv_1gb_activate = findViewById(R.id.tv_1gb_activate);
        tv_1gb_activate.setOnClickListener(this);
        tv_500mb_activate = findViewById(R.id.tv_500mb_activate);
        tv_500mb_activate.setOnClickListener(this);
        tv_150mb_activate = findViewById(R.id.tv_150mb_activate);
        tv_150mb_activate.setOnClickListener(this);
        tv_50mb_activate = findViewById(R.id.tv_50mb_activate);
        tv_50mb_activate.setOnClickListener(this);
        tv_15mb_activate = findViewById(R.id.tv_15mb_activate);
        tv_15mb_activate.setOnClickListener(this);
        tv_7mb_activate = findViewById(R.id.tv_7mb_activate);
        tv_7mb_activate.setOnClickListener(this);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(mContext, imageModelArrayList));
        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
        indicator.setRadius(5 * density);
        NUM_PAGES = imageModelArrayList.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });


    }

    private ArrayList<ImageModel> populateList() {
        ArrayList<ImageModel> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
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
            case R.id.tv_1gb_activate:
                DialogEnterPin.openDialog(mContext);
                break;

            case R.id.tv_500mb_activate:
                DialogEnterPin.openDialog(mContext);
                break;

            case R.id.tv_150mb_activate:
                DialogEnterPin.openDialog(mContext);
                break;

            case R.id.tv_50mb_activate:
                DialogEnterPin.openDialog(mContext);
                break;

            case R.id.tv_15mb_activate:
                DialogEnterPin.openDialog(mContext);
                break;

            case R.id.tv_7mb_activate:
                DialogEnterPin.openDialog(mContext);
                break;

        }

    }
}
