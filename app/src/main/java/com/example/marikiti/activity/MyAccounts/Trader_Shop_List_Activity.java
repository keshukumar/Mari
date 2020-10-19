package com.example.marikiti.activity.MyAccounts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.marikiti.R;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.adapter.shop_list_adapter;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.shop_list_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.HOSTNAME;
import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class Trader_Shop_List_Activity extends AppCompatActivity implements View.OnClickListener {
    String ADD_SHOP_URL=HOSTNAME+"fetch_other_shop.php";
    String ID;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List< shop_list_model> addShop_models;


    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        setContentView(R.layout.activity_trader__shop__list_);
        try {
            ID = getIntent().getStringExtra("id");
        }catch (NullPointerException e){}
        initToolbar();
        findViewID();
        addShop_models = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new shop_list_adapter(addShop_models,Trader_Shop_List_Activity.this);
        recyclerView.setAdapter(adapter);

        if (ID.equals("12"))
        {
            fetchList();
        }else
        {
            volley();
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
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        home = mToolbar.findViewById(R.id.home);
        home.setOnClickListener(this);
        title.setText(getIntent().getStringExtra("name"));
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void findViewID() {
        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(this);
        recyclerView =(RecyclerView)findViewById(R.id.recy_shops_list);
    }


    public void volley()
    {
        final ProgressDialog load=ProgressDialog.show(Trader_Shop_List_Activity.this,"Loading..","Please Wait",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_SHOP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject O = jsonArray.getJSONObject(i);
                        shop_list_model addShop_model = new  shop_list_model(O.getString("other_shop_id"),O.getString("other_shop_name"),
                                O.getString("other_shop_image"));
                        addShop_models.add(addShop_model);
                        adapter.notifyDataSetChanged();


                    }
                    load.dismiss();
                }catch (Exception e)
                {load.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();

                params.put("mshop_id",ID);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.btn_finish:
                finish();
                break;
        }
    }


    private void fetchList() {
        Log.d("kesh","keshav "+getIntent().getStringExtra("id"));
        final ProgressDialog load=ProgressDialog.show(Trader_Shop_List_Activity.this,"Loading..","Please Wait",false,false);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kesh",response);
                if (!TextUtils.isEmpty(response))
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject O = jsonArray.getJSONObject(i);
                            shop_list_model addShop_model = new  shop_list_model(O.getString("other_shop_id"),O.getString("other_shop_name"),
                                    O.getString("other_shop_image"));
                            addShop_models.add(addShop_model);
                            adapter.notifyDataSetChanged();


                        }
                        load.dismiss();
                    }catch (Exception e)
                    {load.dismiss();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("type","shop_item_list");
                params.put("mshop_id",ID);

                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
