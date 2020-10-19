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
import com.example.marikiti.model.ImageModel;
import com.example.marikiti.model.Title;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class TheOutletActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.drawable.add1, R.drawable.add2, R.drawable.add3};
    Title model=new Title();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_the_outlet);
        initToolbar();
        findViewID();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        try{
            String t=getIntent().getStringExtra("title");
            title.setText(t);
        }catch (NullPointerException e){}

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



        img1=findViewById(R.id.outlet_img1);
        img1.setOnClickListener(this);
        img2=findViewById(R.id.outlet_img2);
        img2.setOnClickListener(this);
        img3=findViewById(R.id.outlet_img3);
        img3.setOnClickListener(this);
        img4=findViewById(R.id.outlet_img4);
        img4.setOnClickListener(this);
        img5=findViewById(R.id.outlet_img5);
        img5.setOnClickListener(this);
        img6=findViewById(R.id.outlet_img6);
        img6.setOnClickListener(this);
        img7=findViewById(R.id.outlet_img7);
        img7.setOnClickListener(this);
        img8=findViewById(R.id.outlet_img8);
        img8.setOnClickListener(this);
        img9=findViewById(R.id.outlet_img9);
        img9.setOnClickListener(this);
        img10=findViewById(R.id.outlet_img10);
        img10.setOnClickListener(this);
        img11=findViewById(R.id.outlet_img11);
        img11.setOnClickListener(this);
        img12=findViewById(R.id.outlet_img12);
        img12.setOnClickListener(this);

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

            case R.id.outlet_img1:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Girls Store")
                        .putExtra("position","img1"));
                break;
            case R.id.outlet_img2:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Boys Store")
                        .putExtra("position","img2"));
                break;
            case R.id.outlet_img3:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Babies Store")
                        .putExtra("position","img3"));
                break;
            case R.id.outlet_img4:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Men’s Store")
                        .putExtra("position","img4"));
                break;
            case R.id.outlet_img5:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Women’s Store")
                        .putExtra("position","img5"));
                break;
            case R.id.outlet_img6:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Computer Store")
                        .putExtra("position","img6"));
                break;
            case R.id.outlet_img7:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Office Furnishing")
                        .putExtra("position","img7"));
                break;
            case R.id.outlet_img8:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","JuaKali")
                        .putExtra("position","img8"));
                break;
            case R.id.outlet_img9:

                startActivity(new Intent(mContext,TheOutletLocalKioskActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Local Services")
                        .putExtra("position","img9"));
                break;
            case R.id.outlet_img10:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Home Furnishing")
                        .putExtra("position","img10"));
                break;
            case R.id.outlet_img11:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Kitchen Ware ")
                        .putExtra("position","img11"));
                break;
            case R.id.outlet_img12:
                startActivity(new Intent(mContext,ShopDetailActivity.class)
                        .putExtra("item", "outlet")
                        .putExtra("title","Maasai Market")
                        .putExtra("position","img12"));
                break;
        }
    }
}
