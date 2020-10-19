package com.example.marikiti.activity.MyAccounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.activity.MyCartActivity;
import com.example.marikiti.activity.Outlet_And_Health_Activity;
import com.example.marikiti.adapter.SalesAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;
import static com.example.marikiti.utilities.APP_URL.SUPERMARKET_IMAGE;

public class Sale_Order_View_Deails_Activity extends AppCompatActivity {


Activity activity;
TextView tv_date,tv_name,tv_mobile,tv_price,tv_product_name,tv_qty,
        tv_size,tv_other_details,tv_house_name,tv_house_mobile_no,tv_address,tv_details;
ImageView imageView;
String CART_ID;
ProgressBar load;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    TextView tv_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity=Sale_Order_View_Deails_Activity.this;
        setContentView(R.layout.activity_sale__order__view__deails_);
        CART_ID=getIntent().getStringExtra("cart_id");
        initToolbar();
        intiView();
        fetch_sale_order();
        fetch_mycart(); 
    }

    public void intiView()
    {
        tv_date=findViewById(R.id.date);
        imageView=findViewById(R.id.img_sale_pImage);
        tv_name=findViewById(R.id.sale_name);
        tv_mobile=findViewById(R.id.sale_mobile);
        tv_details=findViewById(R.id.sale_details);
        tv_price=findViewById(R.id.tv_sale_price);
        tv_product_name=findViewById(R.id.sale_product_name);
        tv_qty=findViewById(R.id.sale_qty);
        tv_size=findViewById(R.id.sale_size);
        tv_other_details=findViewById(R.id.sale_other_detail);
        tv_house_name=findViewById(R.id.sale_home_name);
        tv_house_mobile_no=findViewById(R.id.sale_home_mobile);
        tv_address=findViewById(R.id.sale_address);
        load=findViewById(R.id.load);



    }

    public void fetch_sale_order()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
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
                                tv_name.setText(o.getString("full_name"));
                                tv_mobile.setText(o.getString("phone_no"));
                                Glide.with(activity).load(SUPERMARKET_IMAGE+o.getString("image_name")).into(imageView);
                                tv_product_name.setText(o.getString("product_name"));
                                tv_other_details.setText(o.getString("description"));
                                tv_price.setText(o.getString("price"));
                                tv_details.setText("Order Number\n"+o.getString("product_code"));


                        }

                        JSONArray jsonArray2 =jsonObject.getJSONArray("address");

                        for(int i=0;i<jsonArray2.length();i++)
                        {
                            JSONObject o =jsonArray2.getJSONObject(i);
                                tv_house_name.setText(o.getString("name"));
                                tv_house_mobile_no.setText(o.getString("mobile_no"));
                                tv_address.setText(o.getString("ward")+" - "+o.getString("constituency")+" - "+o.getString("county")+"\n"
                                        +o.getString("house_name")+" "+o.getString("house_no")+" "+o.getString("street"));


                        }


                        load.setVisibility(View.GONE);
                    }catch(Exception e)
                    {
                        load.setVisibility(View.GONE);
                        Log.d("response_error",e.toString());
                        Toast.makeText(activity,"Data is not found!",Toast.LENGTH_LONG).show();
                    }
                }else
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(activity,"Data is not found!",Toast.LENGTH_LONG).show();
                load.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","sales_order_details" );
                params.put("mcart_id",CART_ID);

                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Sale Orders");
        home = mToolbar.findViewById(R.id.home);

        mToolbar.setTitle("Sale Orders");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageView mycarttotal=mToolbar.findViewById(R.id.mycart);
        mycarttotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mrcart = new Intent(activity, MyCartActivity.class);
                startActivity(mrcart);
            }
        });

        tv_cart=mToolbar.findViewById(R.id.cart_text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent home = new Intent(activity, HomeActivity.class);
                startActivity(home);
                finish();
                break;

        }
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