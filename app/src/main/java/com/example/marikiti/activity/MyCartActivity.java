package com.example.marikiti.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.example.marikiti.R;
import com.example.marikiti.adapter.MyCartAdapter;
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.adapter.Supermarket_Cart_Adapter;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.model.MyCart;
import com.example.marikiti.model.Supermarket_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class MyCartActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_continue_shopping, btn_checkout;
private ProgressBar p_dialog;
    private RecyclerView rec_my_cart;
    private RecyclerView.Adapter adapter;
    private TextView tv_no_item_found;

    private List<MyCart> myCartList= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_my_cart);
        initToolbar();
        findViewID();
        fetch_item_data();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
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
        tv_no_item_found = findViewById(R.id.tv_no_item_found);
        rec_my_cart = findViewById(R.id.rec_my_cart);
        rec_my_cart.setHasFixedSize(true);
        rec_my_cart.setLayoutManager(new LinearLayoutManager(mContext));

        btn_continue_shopping = findViewById(R.id.btn_continue_shopping);
        btn_checkout = findViewById(R.id.btn_checkout);
        btn_continue_shopping.setOnClickListener(this);
        btn_checkout.setOnClickListener(this);

        p_dialog=findViewById(R.id.p_dilog);
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

            case R.id.btn_continue_shopping:
                Intent continueshopping = new Intent(mContext, HomeActivity.class);
                startActivity(continueshopping);
                finish();
                break;

            case R.id.btn_checkout:
                Intent checkOut = new Intent(mContext, CheckoutActivity.class);
                startActivity(checkOut);
                break;
        }

    }

    public void fetch_item_data()
    {
       myCartList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(MyCartActivity.this);
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
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                            MyCart myCart=new MyCart(o.getString("cart_id"),o.getString("quantity"),o.getString("size"),o.getString("product_name"),
                                    o.getString("price"),o.getString("image_name"),o.getString("product_code"));

                           myCartList.add(myCart);
                        }
                        if (myCartList.size()>0)
                        {
                            p_dialog.setVisibility(View.GONE);
                        }
                        adapter = new MyCartAdapter(MyCartActivity.this, myCartList);
                        rec_my_cart.setAdapter(adapter);


                    }catch(Exception e)
                    {
                        Log.d("responser","error= "+e.toString());

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
                params.put("type","fetch_cart_items" );
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        fetch_item_data();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        fetch_item_data();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        fetch_item_data();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        fetch_item_data();
//    }
}
