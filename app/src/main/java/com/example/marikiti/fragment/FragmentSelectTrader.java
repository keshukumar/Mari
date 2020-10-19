package com.example.marikiti.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.MassaiMarketActivity;
import com.example.marikiti.activity.TheOutletBabyStoreActivity;
import com.example.marikiti.activity.TheOutletBoysStoreActivity;
import com.example.marikiti.activity.TheOutletComputerStoreActivity;
import com.example.marikiti.activity.TheOutletGirlsStoreActivity;
import com.example.marikiti.activity.TheOutletHomeFurnishingActivity;
import com.example.marikiti.activity.TheOutletJuakaliActivity;
import com.example.marikiti.activity.TheOutletKitchenWareActivity;
import com.example.marikiti.activity.TheOutletMenStoreActivity;
import com.example.marikiti.activity.TheOutletOfficeFurnitureActivity;
import com.example.marikiti.activity.TheOutletWomenStoreActivity;
import com.example.marikiti.activity.TraderDetailActivity;
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.adapter.SelectTraderAdapter;
import com.example.marikiti.adapter.SlidingImage_Adapter;
import com.example.marikiti.model.ImageModel;
import com.example.marikiti.model.SelectTrader;
import com.example.marikiti.model.Title;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentSelectTrader extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private View view;
    private RecyclerView rec_select_trader;
    private TextView tv_no_trader_found;
    private SelectTraderAdapter<SelectTrader> selectTraderAdapter;
    private List<SelectTrader> traderList;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.drawable.add1, R.drawable.add2, R.drawable.add3};
    private String item;
Title model=new Title();

    public static FragmentSelectTrader newInstance() {
        FragmentSelectTrader blankFragment = new FragmentSelectTrader();
        return blankFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_select_trader, container, false);
        initView(view);
        initList();
        return view;
    }

    private void initView(View view) {
        Intent intent = getActivity().getIntent();
        item = intent.getStringExtra("item");

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        tv_no_trader_found = view.findViewById(R.id.tv_no_trader_found);
        rec_select_trader = view.findViewById(R.id.rec_select_trader);
        rec_select_trader.setHasFixedSize(true);
        rec_select_trader.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initList() {
        traderList = new ArrayList<>();
        selectTraderAdapter = new SelectTraderAdapter<SelectTrader>(mContext, traderList);
        selectTraderAdapter.setOnRecyclerListItemClickListener(new OnRecyclerListItemClickListener<SelectTrader>() {
            @Override
            public void onItemClick(View view, View rootView, SelectTrader selectTrader, int position) {
                //Toast.makeText(mContext, "click ", Toast.LENGTH_SHORT).show();

                if (item != null && item.equals("pizza")) {
                    Intent detail = new Intent(mContext, TraderDetailActivity.class);
                    detail.putExtra("item", "pizza");
                    startActivity(detail);
                }
                else if (item != null && item.equals("outlet")){
                    clickEvent();
                }
                else {
                    Intent detail = new Intent(mContext, TraderDetailActivity.class);
                    startActivity(detail);
                }


            }
        });
        rec_select_trader.setAdapter(selectTraderAdapter);

        for (int i = 0; i < 20; i++) {
            SelectTrader selectTrader = new SelectTrader();
            selectTrader.setName("Trader Name");
            traderList.add(selectTrader);
            selectTraderAdapter.notifyDataSetChanged();
        }


        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(mContext, imageModelArrayList));
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

        }
    }

    public void clickEvent()
    {
        Intent intent = getActivity().getIntent();
        String position = intent.getStringExtra("position");
        switch (position)
        {
            case "img1"://gift
                Intent fruit = new Intent(mContext, TheOutletGirlsStoreActivity.class);
                model.setTitle("Girls Store");
                fruit.putExtra("item", "outlet");
                startActivity(fruit);
                break;

            case "img2"://boys
                Intent vegetable = new Intent(mContext, TheOutletBoysStoreActivity.class);
                model.setTitle("Girls Store ");
                vegetable.putExtra("item", "outlet");
                startActivity(vegetable);
                break;

            case "img3":// babies
                Intent cereals = new Intent(mContext, TheOutletBabyStoreActivity.class);
                model.setTitle("Girls Store");
                cereals.putExtra("item", "outlet");
                startActivity(cereals);
                break;

            case "img4"://mens store
                Intent butchery = new Intent(mContext, TheOutletMenStoreActivity.class);
                model.setTitle("Men’s Store");
                butchery.putExtra("item", "outlet");
                startActivity(butchery);
                break;

            case "img5":// women store
                Intent dairy = new Intent(mContext, TheOutletWomenStoreActivity.class);
                model.setTitle("");
                dairy.putExtra("item", "outlet");
                startActivity(dairy);
                break;

            case "img6":// computer store
                Intent fishseafood = new Intent(mContext, TheOutletComputerStoreActivity.class);
                model.setTitle("");
                fishseafood.putExtra("item", "outlet");
                startActivity(fishseafood);
                break;

            case "img7": // office
                Intent readymeals = new Intent(mContext, TheOutletOfficeFurnitureActivity.class);
                model.setTitle("");
                readymeals.putExtra("item", "outlet");
                startActivity(readymeals);
                break;

            case "img8": //juakali
                Intent bakery = new Intent(mContext, TheOutletJuakaliActivity.class);
                model.setTitle("");
                bakery.putExtra("item", "outlet");
                startActivity(bakery);
                break;

            case "img9": //local kiosk
                Intent papa_jo_pizza = new Intent(mContext, TraderDetailActivity.class);
                model.setTitle("");
                papa_jo_pizza.putExtra("item", "outlet");
                startActivity(papa_jo_pizza);
                break;

            case "img10": //home funishing
                Intent outlet = new Intent(mContext, TheOutletHomeFurnishingActivity.class);
                model.setTitle("");
                outlet.putExtra("item", "outlet");
                startActivity(outlet);
                break;

            case "img11": //kitech
                Intent health_beauty = new Intent(mContext, TheOutletKitchenWareActivity.class);
                model.setTitle("");
                health_beauty.putExtra("item", "outlet");
                startActivity(health_beauty);
                break;

            case "img12": //maasal
                Intent maasai = new Intent(mContext, MassaiMarketActivity.class);
                model.setTitle("");
                maasai.putExtra("item", "outlet");
                startActivity(maasai);
                break;
        }
    }

}