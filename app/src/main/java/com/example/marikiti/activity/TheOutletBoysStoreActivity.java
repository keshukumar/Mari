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

public class TheOutletBoysStoreActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private ImageView img_fruits, img_vegetables, img_cereals,img_butchery,img_dairy,img_fishseafood,
            img_readymeals,img_bakery,img_papa_jo_pizza,img_outlet,img_health_beauty,img_pharmacy;

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
        setContentView(R.layout.activity_the_outlet_boys_store);
        initToolbar();
        findViewID();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Boys Store ");
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

        img_fruits = findViewById(R.id.img_fruits);
        img_vegetables = findViewById(R.id.img_vegetables);
        img_cereals = findViewById(R.id.img_cereals);
        img_butchery = findViewById(R.id.img_butchery);
        img_dairy = findViewById(R.id.img_dairy);
        img_fishseafood = findViewById(R.id.img_fishseafood);
        img_readymeals = findViewById(R.id.img_readymeals);
        img_bakery = findViewById(R.id.img_bakery);
        img_papa_jo_pizza = findViewById(R.id.img_papa_jo_pizza);
        img_outlet = findViewById(R.id.img_outlet);
        img_health_beauty = findViewById(R.id.img_health_beauty);
        img_pharmacy = findViewById(R.id.img_pharmacy);

        img_fruits.setOnClickListener(this);
        img_vegetables.setOnClickListener(this);
        img_cereals.setOnClickListener(this);
        img_butchery.setOnClickListener(this);
        img_dairy.setOnClickListener(this);
        img_fishseafood.setOnClickListener(this);
        img_readymeals.setOnClickListener(this);
        img_bakery.setOnClickListener(this);
        img_papa_jo_pizza.setOnClickListener(this);
        img_outlet.setOnClickListener(this);
        img_health_beauty.setOnClickListener(this);
        img_pharmacy.setOnClickListener(this);

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


            case R.id.img_fruits:
                Intent fruit = new Intent(mContext, TraderDetailActivity.class);
                fruit.putExtra("item", "outlet");
                model.setTitle("Belts and Socks");
                startActivity(fruit);
                break;
            case R.id.img_vegetables:
                Intent vegetable = new Intent(mContext, TraderDetailActivity.class);
                vegetable.putExtra("item", "outlet");
                model.setTitle("Casual Shoes");
                startActivity(vegetable);
                break;
            case R.id.img_cereals:
                Intent cereals = new Intent(mContext, TraderDetailActivity.class);
                cereals.putExtra("item", "outlet");
                model.setTitle("Formal Shoes");
                startActivity(cereals);
                break;
            case R.id.img_butchery:
                Intent butchery = new Intent(mContext, TraderDetailActivity.class);
                butchery.putExtra("item", "outlet");
                model.setTitle("Boys T-Shirts ");
                startActivity(butchery);
                break;
            case R.id.img_dairy:
                Intent dairy = new Intent(mContext, TraderDetailActivity.class);
                dairy.putExtra("item", "outlet");
                model.setTitle("Boys Jackets");
                startActivity(dairy);
                break;
            case R.id.img_fishseafood:
                Intent fishseafood = new Intent(mContext, TraderDetailActivity.class);
                fishseafood.putExtra("item", "outlet");
                model.setTitle("School Uniform");
                startActivity(fishseafood);
                break;
            case R.id.img_readymeals:
                Intent readymeals = new Intent(mContext, TraderDetailActivity.class);
                readymeals.putExtra("item", "outlet");
                model.setTitle("Inner Wear");
                startActivity(readymeals);
                break;
            case R.id.img_bakery:
                Intent bakery = new Intent(mContext, TraderDetailActivity.class);
                bakery.putExtra("item", "outlet");
                model.setTitle("Boys Pyjamas");
                startActivity(bakery);
                break;
            case R.id.img_papa_jo_pizza:
                Intent papa_jo_pizza = new Intent(mContext, TraderDetailActivity.class);
                papa_jo_pizza.putExtra("item", "outlet");
                model.setTitle("Boys Pyjamas");
                startActivity(papa_jo_pizza);
                break;
            case R.id.img_outlet:
                Intent outlet = new Intent(mContext, TraderDetailActivity.class);
                outlet.putExtra("item", "outlet");
                model.setTitle("Sports Ware");
                startActivity(outlet);
                break;
            case R.id.img_health_beauty:
                Intent health_beauty = new Intent(mContext, TraderDetailActivity.class);
                health_beauty.putExtra("item", "outlet");
                model.setTitle("Boys Trousers");
                startActivity(health_beauty);
                break;
            case R.id.img_pharmacy:
                Intent pharmacy = new Intent(mContext, TraderDetailActivity.class);
                pharmacy.putExtra("item", "outlet");
                model.setTitle("Sales");
                startActivity(pharmacy);
                break;
        }
    }
}
