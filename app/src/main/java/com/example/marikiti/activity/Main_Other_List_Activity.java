package com.example.marikiti.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marikiti.R;

import com.example.marikiti.adapter.SlidingImage_Adapter;

import com.example.marikiti.fragment.FragmentTraderSearch;
import com.example.marikiti.fragment.Trader_List_Fragment;
import com.example.marikiti.model.ImageModel;
import com.example.marikiti.model.Main_Transfer;

import com.example.marikiti.utilities.NonSwipeViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class Main_Other_List_Activity extends AppCompatActivity
{
    private NonSwipeViewPager viewPager;
    private TabLayout tabs;
    Main_Transfer model;
    private int[] myImageList;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private String STATUS;
TextView tv_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__other__list_);

        if ((model.getShop_id().equals("12")))
        {
            myImageList= new int[]{R.drawable.supermarket1,R.drawable.supermarket3,R.drawable.supermarket4,
                    R.drawable.supermarket5,R.drawable.supermarket6,R.drawable.supermarket7,R.drawable.supermarket8};
        }else
        {
            myImageList= new int[]{R.drawable.main_menu1, R.drawable.main_menu2, R.drawable.main_menu3a};
        }
        initToolbar();
        sliderSetup();
        findViewID();

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

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar mToolbar;
        GTextView title;
        ImageView home;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        try
        { title.setText(model.getShop_name());
        }catch (NullPointerException e){}
        home = mToolbar.findViewById(R.id.home);
        if ((model.getShop_id().equals("3")))
        {
           home.setBackgroundResource(R.drawable.papa_jo);
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageView mycarttotal=mToolbar.findViewById(R.id.mycart);
        mycarttotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mrcart = new Intent(Main_Other_List_Activity.this, MyCartActivity.class);
                startActivity(mrcart);
            }
        });

        tv_cart=mToolbar.findViewById(R.id.cart_text);
    }


    public void sliderSetup()
    {
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(Main_Other_List_Activity.this, imageModelArrayList));
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
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
        for (int i = 0; i < myImageList.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
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
                    fragment = new FragmentTraderSearch();
                    break;

                case 1:
                    fragment = new Trader_List_Fragment();
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


    public void fetch_mycart()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Main_Other_List_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resposnse","response= "+response);
                tv_cart.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","total_no_item" );
                SharedPreferences sp=getApplicationContext().getSharedPreferences("user_detail",MODE_PRIVATE);
                params.put("muser_id",sp.getString("user_key",""));
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_mycart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch_mycart();
    }

}
