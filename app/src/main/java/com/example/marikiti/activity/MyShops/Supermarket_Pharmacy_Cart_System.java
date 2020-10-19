package com.example.marikiti.activity.MyShops;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyCartActivity;
import com.example.marikiti.adapter.Supermarket_Cart_Adapter;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.Supermarket_model;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;
import static com.example.marikiti.utilities.APP_URL.SUPERMARKET_IMAGE;

public class Supermarket_Pharmacy_Cart_System extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView  item_sub_recycler;
    private Button btn_addtocart;
    private RecyclerView.Adapter item_adapter;
    private GridLayoutManager item_grid;
    private Activity activity;
    private List<Supermarket_model> item_list=new ArrayList<>();
    private ProgressBar item_sub_progressbar;
    private Main_Transfer main_transfer=new Main_Transfer();
    LinearLayout layout;
    SharedPreferences sp;
    Constant c=new Constant();
    private EditText searchText;
    private ImageButton searchBtn;
    TextView cart_item_name,cart_item_sub_name,cart_price,item_code,tv_msg,tv_product_id,tv_cart;
    ImageView cart_image,mycarttotal;
    String item_name,item_sub_name,price,image_name,code,category;
    GTextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity=this;
        setContentView(R.layout.activity_supermarket__pharmacy__cart__system);
        sp=activity.getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
        initToolbar();
        intiView();
        searchText.setText(main_transfer.getItem_name());
        fetch_item_data(main_transfer.getItem_name());
        fetch_mycart();
    }

    public void intiView()
    {
        tv_msg=findViewById(R.id.a);
        cart_item_name=findViewById(R.id.cart_item_name);
        cart_item_sub_name=findViewById(R.id.cart_item_sub_name);
        cart_price=findViewById(R.id.cart_price);
        cart_image=findViewById(R.id.cart_image);
        item_code=findViewById(R.id.cart_item_code);
        layout=findViewById(R.id.cart_layout);
        item_sub_recycler =findViewById(R.id.cart_item_sub_recycler);
        searchText=findViewById(R.id.search_edit);
        searchBtn=findViewById(R.id.searchbtn);
        tv_product_id=findViewById(R.id.product_id);
        btn_addtocart=findViewById(R.id.cart_addtocart);
        btn_addtocart.setOnClickListener(this);
        item_sub_progressbar=findViewById(R.id.cart_item_sub_progressBar);
        searchBtn=findViewById(R.id.searchbtn);
        searchText=findViewById(R.id.search_edit);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(searchText.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(activity, "Please Fill Search Data.!", Toast.LENGTH_SHORT).show();
                }else
                {
                    fetch_item_data(searchText.getText().toString().trim());
                }

            }
        });


        setup_recyclerview();
    }

    public void setup_recyclerview()
    {
        item_grid=new GridLayoutManager(activity,1,RecyclerView.VERTICAL,false);
        item_sub_recycler.setLayoutManager(item_grid);
        // item_adapter=new Cart_item_adapter(activity,item_list,main_transfer.getTrader_id());
        // item_sub_recycler.setAdapter(item_adapter);

    }

    @Override
    public void onClick(View v)
    {

    }




    public void fetch_item_data(final String search_data)
    {
        layout.setVisibility(View.GONE);
        item_sub_progressbar.setVisibility(View.VISIBLE);
        item_list.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","item= "+response);
                Supermarket_model supermarket_model;
                if (!TextUtils.isEmpty(response))
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response1");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                            supermarket_model=new Supermarket_model(o.getString("id"),o.getString("product_category"),o.getString("product_name"),
                                    o.getString("product_code"),o.getString("price"),o.getString("description"),o.getString("image_name"));
                            item_list.add(supermarket_model);
                        }
                        item_adapter=new Supermarket_Cart_Adapter(activity,item_list);
                        item_sub_recycler.setAdapter(item_adapter);
                        item_adapter.notifyDataSetChanged();


                    }catch(Exception e)
                    {
                        Log.d("responser","error= "+e.toString());
                        item_sub_progressbar.setVisibility(View.INVISIBLE);
                    }

                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                            Log.d("response","product_name"+o.getString("product_name")+"\nproduct_category"+o.getString("product_category")
                                    +"\nprice"+o.getString("price"));
                            category=o.getString("product_category");
                            item_name=o.getString("product_name");
                            item_sub_name=o.getString("description");
                            tv_product_id.setText(o.getString("id"));
                            price=o.getString("price");
                            image_name=o.getString("image_name");
                            code=o.getString("product_code");
                            showdata();
                        }


                    }catch(Exception e)
                    {
                        Log.d("responser","error= "+e.toString());
                        item_sub_progressbar.setVisibility(View.INVISIBLE);
                    }
                    item_sub_progressbar.setVisibility(View.INVISIBLE);
                    layout.setVisibility(View.VISIBLE);

                }else
                {
                    tv_msg.setText("Temporary Out of Stock");
                    item_sub_progressbar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                tv_msg.setText(" Temporary Out of Stock");
                item_sub_progressbar.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","search_supermarket" );
                params.put("search_data",search_data);

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
    private void initToolbar() {
        Toolbar mToolbar;

        ImageView home;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        try{
            title.setText(main_transfer.getShop_name());
        }catch (NullPointerException e){}

        mycarttotal=mToolbar.findViewById(R.id.mycart);
        mycarttotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mrcart = new Intent(activity, MyCartActivity.class);
                startActivity(mrcart);
            }
        });

        tv_cart=mToolbar.findViewById(R.id.cart_text);



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

    public void showdata()
    {
        title.setText(category);
        cart_item_name.setText(item_name);
        cart_item_sub_name.setText(item_sub_name);
        cart_price.setText(price);
        item_code.setText(code);
        Glide.with(Supermarket_Pharmacy_Cart_System.this).load(SUPERMARKET_IMAGE+image_name).into(cart_image);

    }


    public void fetch_mycart()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
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
                params.put("muser_id",sp.getString(c.USER_KEY,""));
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
