package com.example.marikiti.activity.MyAccounts;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
import com.example.marikiti.activity.Cart_Activitty;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.adapter.Cart_sub_adapter;
import com.example.marikiti.adapter.SalesAdapter;
import com.example.marikiti.model.SalesStatement;
import com.example.marikiti.model.cart_model;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.CATEGORY_IMAGES;
import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class MyAccount_SalesActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private RecyclerView rec_sales;
    private SalesAdapter salesStatementAdapter;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
     List<Sale_Order_Model> salesStatementList=new ArrayList<>();
    Constant constant = new Constant();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_my_account__sales);

        initToolbar();
        initView();
        initList();
    }

    public void initView()
    {
        rec_sales=findViewById(R.id.sale_recyclerview);
        rec_sales.setHasFixedSize(true);
        rec_sales.setLayoutManager(new LinearLayoutManager(mContext));
        fetch_sale_order();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Sale Orders");
        home = mToolbar.findViewById(R.id.home);
        home.setOnClickListener(this);
        mToolbar.setTitle("Sale Orders");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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

        }
    }

    private void initList() {



    }
    public void fetch_sale_order()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MyAccount_SalesActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","keshav="+response);
                if (!TextUtils.isEmpty(response))
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray1 =jsonObject.getJSONArray("server_response");

                        for(int i=0;i<jsonArray1.length();i++)
                        {
                            JSONObject o =jsonArray1.getJSONObject(i);
                           Sale_Order_Model model=new Sale_Order_Model(o.getString("cart_id"),o.getString("full_name"),
                                   o.getString("phone_no"),o.getString("image_name"),o.getString("description"),
                                   o.getString("price"),o.getString("cart_date"),o.getString("product_code"));

                            salesStatementList.add(model);
                        }
                        salesStatementAdapter = new SalesAdapter(mContext, salesStatementList);
                        rec_sales.setAdapter(salesStatementAdapter);

                    }catch(Exception e)
                    {
                        Log.d("response_error",e.toString());
                    }
                }else
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","sales_order" );
                sp=getApplicationContext().getSharedPreferences(constant.USER_DETAILS,MODE_PRIVATE);
                params.put("mtrader_id",sp.getString(constant.TRADER_ID,""));





                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
