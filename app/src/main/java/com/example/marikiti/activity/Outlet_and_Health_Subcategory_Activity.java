package com.example.marikiti.activity;

import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marikiti.R;
import com.example.marikiti.adapter.Outlet_And_Health_Subcategory_Adapter;
import com.example.marikiti.adapter.SlidingImage_Adapter;
import com.example.marikiti.model.ImageModel;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.Other_Shop_List_Model;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class Outlet_and_Health_Subcategory_Activity extends AppCompatActivity {
    Main_Transfer model;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Other_Shop_List_Model> list = new ArrayList<>();
    ProgressBar progressBar;
    Main_Transfer transfer;
    private int[] myImageList;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_and__health__subcategory_);

        initToolbar();
        setupRecycler();
        fetchList();
        Log.d("kesh","id"+model.getShop_id());
        if ((model.getShop_id().equals("12")))
        {
            myImageList= new int[]{R.drawable.supermarket1,R.drawable.supermarket3,R.drawable.supermarket4,
                    R.drawable.supermarket5,R.drawable.supermarket6,R.drawable.supermarket7,R.drawable.supermarket8};
        }else
        {
            myImageList= new int[]{R.drawable.main_menu1, R.drawable.main_menu2, R.drawable.main_menu3a};
        }
        sliderSetup();
    }

    public void setupRecycler()
    {
        recyclerView=findViewById(R.id.outlet_recycler);
        progressBar=findViewById(R.id.outlet_progressbar);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager=new GridLayoutManager(this,3,1,false);
        recyclerView.setLayoutManager(manager);

    }

    private void fetchList() {
        list.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                if (!TextUtils.isEmpty(response))
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        if (model.getShop_id().equals("12"))
                        {
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject o =jsonArray.getJSONObject(i);

                                    Log.d("response","resp get"+model.getShop_id());
                                    Other_Shop_List_Model models=new Other_Shop_List_Model(model.getShop_id(),o.getString("item_name"),o.getString("item_image"),"");
                                    list.add(models);

                                }
                                adapter=new Outlet_And_Health_Subcategory_Adapter(Outlet_and_Health_Subcategory_Activity.this,list);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.INVISIBLE);
                        }else if (model.getShop_id().equals("10")||model.getShop_id().equals("11"))
                        {
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject o =jsonArray.getJSONObject(i);

                                Log.d("response","resp get"+model.getShop_id());
                                Other_Shop_List_Model models=new Other_Shop_List_Model(model.getShop_id(),o.getString("item_name"),o.getString("item_image"),"");
                                list.add(models);

                            }
                            adapter=new Outlet_And_Health_Subcategory_Adapter(Outlet_and_Health_Subcategory_Activity.this,list);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                                for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject o =jsonArray.getJSONObject(i);
                                Other_Shop_List_Model model=new Other_Shop_List_Model(o.getString("item_id"),o.getString("item_name"),o.getString("item_image"),"");
                                list.add(model);

                            }
                                adapter=new Outlet_And_Health_Subcategory_Adapter(Outlet_and_Health_Subcategory_Activity.this,list);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.INVISIBLE);
                        }

                    }catch(Exception e)
                    {
                        Log.d("response_error",e.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","item_List");
                Log.d("response","shop_id"+model.getShop_id());
                params.put("mshop_id",model.getShop_id());
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    private void initToolbar()
    {
        Toolbar mToolbar;
        GTextView title;
        ImageView home;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        try{
            title.setText(transfer.getShop_name());
        }catch (NullPointerException e){}

        home = mToolbar.findViewById(R.id.home);
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
    }

    public void sliderSetup()
    {
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(Outlet_and_Health_Subcategory_Activity.this, imageModelArrayList));
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
}
