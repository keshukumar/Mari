package com.example.marikiti.activity;

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
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.example.marikiti.R;
import com.example.marikiti.adapter.CheckoutAdapter;
import com.example.marikiti.adapter.MyCartAdapter;
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.dialog.DialogEnterPin;
import com.example.marikiti.model.Checkout;
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

public class CheckoutActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_complete_payment;
    private Spinner spn_payment_method;
    private RecyclerView rec_checkout;
    private TextView tv_no_item_found;
    private RecyclerView.Adapter checkoutAdapter;
    private List<MyCart> checkoutList=new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_checkout);
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
        rec_checkout = findViewById(R.id.rec_checkout);
        rec_checkout.setHasFixedSize(true);
        rec_checkout.setLayoutManager(new LinearLayoutManager(mContext));

        btn_complete_payment = findViewById(R.id.btn_complete_payment);
        btn_complete_payment.setOnClickListener(this);

        spn_payment_method = findViewById(R.id.spn_payment_method);
        spn_payment_method.setSelection(0,false);
        spn_payment_method.setOnItemSelectedListener(this);

        progressBar=findViewById(R.id.p_dialog);

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

            case R.id.btn_complete_payment:
                Intent completePayment = new Intent(mContext, HomeActivity.class);
                startActivity(completePayment);
                finish();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                DialogEnterPin.openDialog(mContext);
               // Toast.makeText(parent.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                DialogEnterPin.openDialog(mContext);
                //Toast.makeText(parent.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                DialogEnterPin.openDialog(mContext);
                //Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                DialogEnterPin.openDialog(mContext);
                //Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                DialogEnterPin.openDialog(mContext);
                //Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                //DialogEnterPin.openDialog(mContext);
                //Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void fetch_item_data()
    {

        RequestQueue requestQueue = Volley.newRequestQueue(CheckoutActivity.this);
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

                            checkoutList.add(myCart);
                        }

                        if (checkoutList.size()>0)
                        {
                            progressBar.setVisibility(View.GONE);
                        }
                      checkoutAdapter = new CheckoutAdapter(CheckoutActivity.this, checkoutList);
                      rec_checkout.setAdapter( checkoutAdapter );


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
}
