package com.example.marikiti.activity.MyShops;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.adapter.EditStock_ViewProductAdapter;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.PRODUCT_CATEGORY;

public class EditStock_ViewProduct extends AppCompatActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Activity mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    RecyclerView.Adapter adapter;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_finish;
    private RecyclerView recyclerView;
    private TextView tx_trader_name,tx_trader_code,tv_titel;
    static String product_id,shop_id,trader_id;
    List<View_Product_Model> list=new ArrayList<>();
    Constant c=new Constant();
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_edit_stock__view_product);

        sp=getApplicationContext().getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
        ed=sp.edit();

        try
        {
            product_id=getIntent().getStringExtra("mproduct_id");
            shop_id=getIntent().getStringExtra("mshop_id");
            trader_id=getIntent().getStringExtra("mtrader_id");
        }catch (Exception e){}
        initToolbar();
        findViewID();
        setupList();
        fetch_post();
        Log.d("response", product_id+"\n"+shop_id+"\n"+trader_id+"response=");
    }
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("View Product");
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
        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(this);

        tv_titel=findViewById(R.id.viewproduct_title);
        try
        {
            String t=getIntent().getStringExtra("name");
            tv_titel.setText(t);
        }catch (NullPointerException e){}

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
            case R.id.btn_finish:
                Intent finish = new Intent(mContext, HomeActivity.class);
                startActivity(finish);
                finish();
                break;

        }

    }


    public void setupList()
    {
        recyclerView=findViewById(R.id.viewproduct_recycler);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(EditStock_ViewProduct.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


    public void fetch_post()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PRODUCT_CATEGORY, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.d("response", product_id+"\n"+shop_id+"\n"+trader_id+"response=" + response);
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject o = jsonArray.getJSONObject(i);
                            View_Product_Model model = new View_Product_Model(o.getString("trader_shop_item_category_id"), o.getString("image_name"), o.getString("product_category"),o.getString("mdescription"));
                            list.add(model);

                        }
                        adapter = new EditStock_ViewProductAdapter(mContext, list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        load.dismiss();
                    } catch (Exception e) {
                        Log.d("response_error", e.toString());
                        load.dismiss();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    Log.d("response_error",error.toString());
                    load.dismiss();
                }
            })
            {

                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();

                params.put("mproduct_id",product_id);
                params.put("mshop_id",shop_id);
                params.put("mtrader_id",sp.getString(c.TRADER_ID,""));
                return params;
            }
            };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
        }

    }

