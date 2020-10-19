package com.example.marikiti.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marikiti.R;
import com.example.marikiti.activity.HealthAndBeautyActivity;
import com.example.marikiti.activity.ShopDetailActivity;
import com.example.marikiti.activity.TheOutletActivity;
import com.example.marikiti.adapter.SlidingImage_Adapter;
import com.example.marikiti.model.ImageModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentHome extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private Context gContext;
    private View view;
    private ImageView img_fruits, img_vegetables, img_cereals, img_butchery, img_dairy, img_fishseafood,
            img_readymeals, img_bakery, img_papa_jo_pizza, img_outlet, img_health_beauty, img_pharmacy;
   private static ViewPager mPager;
   private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
   private int[] myImageList = new int[]{R.drawable.main_menu1, R.drawable.main_menu2, R.drawable.main_menu3a};


    public static FragmentHome newInstance() {
        FragmentHome blankFragment = new FragmentHome();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        img_fruits = view.findViewById(R.id.img_fruits);
        img_vegetables = view.findViewById(R.id.img_vegetables);
        img_cereals = view.findViewById(R.id.img_cereals);
        img_butchery = view.findViewById(R.id.img_butchery);
        img_dairy = view.findViewById(R.id.img_dairy);
        img_fishseafood = view.findViewById(R.id.img_fishseafood);
        img_readymeals = view.findViewById(R.id.img_readymeals);
        img_bakery = view.findViewById(R.id.img_bakery);
        img_papa_jo_pizza = view.findViewById(R.id.img_papa_jo_pizza);
        img_outlet = view.findViewById(R.id.img_outlet);
        img_health_beauty = view.findViewById(R.id.img_health_beauty);
        img_pharmacy = view.findViewById(R.id.img_pharmacy);

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

        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(gContext, imageModelArrayList));
        CirclePageIndicator indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_fruits:
                Intent fruit = new Intent(gContext, ShopDetailActivity.class);
                fruit.putExtra("title","Fruits Traders");
                startActivity(fruit);
                break;
            case R.id.img_vegetables:
                Intent vegetable = new Intent(gContext, ShopDetailActivity.class);
                vegetable.putExtra("title","Vegetable Traders");
                startActivity(vegetable);
                break;
            case R.id.img_cereals:
                Intent cereals = new Intent(gContext, ShopDetailActivity.class);
                cereals.putExtra("title","Cereals Traders");
                startActivity(cereals);
                break;
            case R.id.img_butchery:
                Intent butchery = new Intent(gContext, ShopDetailActivity.class);
                butchery.putExtra("title","Dairy Traders");
                startActivity(butchery);
                break;
            case R.id.img_dairy:
                Intent dairy = new Intent(gContext, ShopDetailActivity.class);
                dairy.putExtra("title","Dairy Traders");
                startActivity(dairy);
                break;
            case R.id.img_fishseafood:
                Intent fishseafood = new Intent(gContext, ShopDetailActivity.class);
                fishseafood.putExtra("title","Dairy Traders");
                startActivity(fishseafood);
                break;
            case R.id.img_readymeals:
                Intent readymeals = new Intent(gContext, ShopDetailActivity.class);
                readymeals.putExtra("title","Dairy Traders");
                startActivity(readymeals);
                break;
            case R.id.img_bakery:
                Intent bakery = new Intent(gContext, ShopDetailActivity.class);
                bakery.putExtra("title","Bakery");
                startActivity(bakery);
                break;
            case R.id.img_papa_jo_pizza:
                Intent papa_jo_pizza = new Intent(gContext, ShopDetailActivity.class);
                papa_jo_pizza.putExtra("title","Papa Jo Pizza");
                papa_jo_pizza.putExtra("item", "pizza");
                startActivity(papa_jo_pizza);
                break;
            case R.id.img_outlet:
                Intent outlet = new Intent(gContext, TheOutletActivity.class);
                outlet.putExtra("title","The Outlet");
                startActivity(outlet);
                break;
            case R.id.img_health_beauty:
                Intent health_beauty = new Intent(gContext, HealthAndBeautyActivity.class);
                health_beauty.putExtra("title","Health & Beauty");
                startActivity(health_beauty);
                break;
            case R.id.img_pharmacy:
                Intent pharmacy = new Intent(gContext, ShopDetailActivity.class);
                pharmacy.putExtra("title","Pharmacy");
                startActivity(pharmacy);
                break;

        }
    }


}