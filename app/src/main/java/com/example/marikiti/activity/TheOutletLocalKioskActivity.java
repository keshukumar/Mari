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

public class TheOutletLocalKioskActivity extends BaseActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_the_outlet_local_kiosk);
        initToolbar();
        findViewID();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Local Services");
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
                Intent fruit = new Intent(mContext, ShopDetailActivity.class);
                fruit.putExtra("item", "outlet");
                fruit.putExtra("position","img9");
                model.setTitle("Charcoal");
                startActivity(fruit);
                break;
            case R.id.img_vegetables:
                Intent vegetable = new Intent(mContext, ShopDetailActivity.class);
                vegetable.putExtra("item", "outlet");
              vegetable.putExtra("position","img9");
                model.setTitle("Gas Supply");
                startActivity(vegetable);
                break;
            case R.id.img_cereals:
                Intent cereals = new Intent(mContext, ShopDetailActivity.class);
                cereals.putExtra("item", "outlet");
                cereals.putExtra("position","img9");
                model.setTitle("Gas Supply");
                startActivity(cereals);
                break;
            case R.id.img_butchery:
                Intent butchery = new Intent(mContext, ShopDetailActivity.class);
                butchery.putExtra("item", "outlet");
                butchery.putExtra("position","img9");
                model.setTitle("Electronic Repair");
                startActivity(butchery);
                break;
            case R.id.img_dairy:
                Intent dairy = new Intent(mContext, ShopDetailActivity.class);
                dairy.putExtra("item", "outlet");
                dairy.putExtra("position","img9");
                model.setTitle("Flower Shop");
                startActivity(dairy);
                break;
            case R.id.img_fishseafood:
                Intent fishseafood = new Intent(mContext, ShopDetailActivity.class);
                fishseafood.putExtra("item", "outlet");
                fishseafood.putExtra("position","img9");
                model.setTitle("Fridge Repair");
                startActivity(fishseafood);
                break;
            case R.id.img_readymeals:
                Intent readymeals = new Intent(mContext, ShopDetailActivity.class);
                readymeals.putExtra("item", "outlet");
                readymeals.putExtra("position","img9");
                model.setTitle("Fridge Repair");
                startActivity(readymeals);
                break;
            case R.id.img_bakery:
                Intent bakery = new Intent(mContext, ShopDetailActivity.class);
                bakery.putExtra("item", "outlet");
                bakery.putExtra("position","img9");
                model.setTitle("Phone Repair");
                startActivity(bakery);
                break;
            case R.id.img_papa_jo_pizza:
                Intent papa_jo_pizza = new Intent(mContext, ShopDetailActivity.class);
                papa_jo_pizza.putExtra("item", "outlet");
                papa_jo_pizza.putExtra("position","img9");
                model.setTitle("Tailors");
                startActivity(papa_jo_pizza);
                break;
            case R.id.img_outlet:
                Intent outlet = new Intent(mContext, ShopDetailActivity.class);
                outlet.putExtra("item", "outlet");
                outlet.putExtra("position","img9");
                model.setTitle("Vehicle Repair");
                startActivity(outlet);
                break;
            case R.id.img_health_beauty:
                Intent health_beauty = new Intent(mContext, ShopDetailActivity.class);
                health_beauty.putExtra("item", "outlet");
                health_beauty.putExtra("position","img9");
                model.setTitle("Travel Agent ");
                startActivity(health_beauty);
                break;
            case R.id.img_pharmacy:
                Intent pharmacy = new Intent(mContext, ShopDetailActivity.class);
                pharmacy.putExtra("item", "outlet");
                pharmacy.putExtra("position","img9");
                model.setTitle("Local Kiosk ");
                startActivity(pharmacy);
                break;
        }
    }
}
