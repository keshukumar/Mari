package com.example.marikiti.activity;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.adapter.Cart_sub_adapter;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.cart_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.CATEGORY_IMAGES;
import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class Cart_Activitty extends AppCompatActivity {


    private Spinner sp_quantity,bakery_size,bakery_prefernce;
    private Button btn_addtocart;
    private GridLayoutManager item_grid;
    private Activity activity;
    private ProgressBar item_proressbar;
    private Main_Transfer main_transfer=new Main_Transfer();
    LinearLayout layout,fruits_layout,cereals_layout,butchery_layout,fish_layout,bakery_layout;
    Activity context;
    List<cart_model> sub_item_list=new ArrayList<>();
    String trader_id;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private ImageView item_image;
    private TextView tv_item_name,tv_sub_item_name,tv_price;
    private ProgressBar progressBar;
    GTextView title;
    CheckBox fruit_cut,fruit_mixed,cereals_cooked,butchery_cut,butchery_cooked,fish_fried,fish_cut,fish_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity=this;
        this.context=this;
        setContentView(R.layout.activity_cart__activitty);
        initToolbar();
        intiView();
    }

    public void intiView()
    {
        sp_quantity=findViewById(R.id.cart_spinner);
        layout=findViewById(R.id.cart_layout);
        btn_addtocart=findViewById(R.id.cart_addtocart);
        item_proressbar=findViewById(R.id.cart_item_progressBar);
        tv_item_name=findViewById(R.id.cart_item_name);
        tv_sub_item_name=findViewById(R.id.cart_item_sub_name);
        tv_price=findViewById(R.id.cart_price);
        item_image=findViewById(R.id.cart_image);
        progressBar=findViewById(R.id.cart_item_sub_progressBar);
        layout=findViewById(R.id.cart_layout);
        title =findViewById(R.id.title);

        recyclerView=findViewById(R.id.cart_item_sub_recycler);
        recyclerView.setHasFixedSize(false);
        GridLayoutManager manager=new GridLayoutManager(context,2,0,false);
        recyclerView.setLayoutManager(manager);


        bakery_size=findViewById(R.id.sp_size);
        bakery_prefernce=findViewById(R.id.sp_preference);

        fruit_cut=findViewById(R.id.fruits_cut);
        fruit_mixed=findViewById(R.id.fruits_mixed);

        setup_recyclerview();
      //  showSave();

    }

    public void setup_recyclerview()
    {
        item_grid=new GridLayoutManager(activity,1,0,false);
        recyclerView.setLayoutManager(item_grid);
        fetch_item_sub_data();
    }



    public void fetch_item_sub_data()
    {


        sub_item_list.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(Cart_Activitty.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
Log.d("response","keshav="+response);
                if (!TextUtils.isEmpty(response))
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                            tv_item_name.setText(o.getString("item_name"));
                            if (o.getString("mdescription").equals("null"))
                            {
                                tv_sub_item_name.setText("Description is not available.");
                            }else
                            {
                                tv_sub_item_name.setText(o.getString("mdescription"));
                            }
                            Glide.with(context).load(CATEGORY_IMAGES+o.getString("image_name")).into(item_image);

                        }

                        JSONArray jsonArray1 =jsonObject.getJSONArray("server_response1");
                        sub_item_list.clear();
                        for(int i=0;i<jsonArray1.length();i++)
                        {
                            JSONObject o =jsonArray1.getJSONObject(i);
                            cart_model cart_model=new cart_model(o.getString("image_name"),o.getString("item_name"),o.getString("product_category"),o.getString("mdescription"));
                            sub_item_list.add(cart_model);

                        }
                        layout.setVisibility(View.VISIBLE);
                        adapter=new Cart_sub_adapter(context,sub_item_list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        item_proressbar.setVisibility(View.GONE);
                    }catch(Exception e)
                    {
                        Log.d("response_error",e.toString());
                    }
                }else
                {
                    item_proressbar.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                }
                item_proressbar.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                item_proressbar.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","fetch_sub_item" );
                params.put("mtrader_id",main_transfer.getTrader_id());


                if (main_transfer.getMainCategoryId().equals("10")||main_transfer.getMainCategoryId().equals("11"))
                {
                   // Log.d("response","su-trader="+trader_id+" shop id="+main_transfer.getShop_id()+" item="+shop_id);
                    params.put("mshop_id",main_transfer.getItem_id());
                    params.put("mtypes","subcategory");
                }else
                {
                    //Log.d("response","shop-trader="+trader_id+" shop id="+main_transfer.getShop_id()+" item="+shop_id);
                    params.put("mshop_id",main_transfer.getShop_id());
                    params.put("mtypes","shop");
                }



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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private void initToolbar() {
        Toolbar mToolbar;
        ImageView home;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        GTextView title = (GTextView) mToolbar.findViewById(R.id.title);
        try{
            title.setText(main_transfer.getShop_name());
        }catch (NullPointerException e){}

        home = mToolbar.findViewById(R.id.home);
        if ((main_transfer.getShop_id().equals("3")))
        {
            home.setBackgroundResource(R.drawable.papa_jo);
        }

        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    public void showSave()
    {
        Log.d("response","trader="+main_transfer.getTrader_id()+
                "\nshop name="+main_transfer.getShop_name()+
                "\n shop_id="+main_transfer.getShop_id()+
                "\n item name="+main_transfer.getItem_name()
                +"\n item id="+main_transfer.getItem_id()
                +"\n main="+main_transfer.getMainCategoryId());
    }

}
