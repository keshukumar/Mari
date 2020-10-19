package com.example.marikiti.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.adapter.AddShopAdapter;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.AddShop_Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.SHOP_LIST;

public class AddShopsActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_finish;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<AddShop_Model> addShop_models;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      this.mContext = this;
        setContentView(R.layout.activity_add_shops);
        initToolbar();
        findViewID();
        addShop_models = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddShopAdapter(addShop_models,AddShopsActivity.this);
        recyclerView.setAdapter(adapter);
        fetch_shop_list();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        home = mToolbar.findViewById(R.id.home);
        home.setOnClickListener(this);
       // mToolbar.setTitle("Shop List");
        title.setText("Shop List");
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
                Intent finish = new Intent(getApplicationContext(), Trader_shops_list.class);
                finish.putExtra("id",getIntent().getStringExtra("tarder_id"));
                startActivity(finish);
               // finish();
                break;

        }

    }


    public void fetch_shop_list()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SHOP_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    Log.d("response",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject O = jsonArray.getJSONObject(i);
                        AddShop_Model addShop_model = new AddShop_Model(O.getString("shop_id"),O.getString("shop_name"),
                                O.getString("image_name"));
                        addShop_models.add(addShop_model);
                        adapter.notifyDataSetChanged();
                    }

                    load.dismiss();
                }catch (Exception e)
                {
                    load.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load.dismiss();
            }
        });

        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }
}
