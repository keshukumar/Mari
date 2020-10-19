package com.example.marikiti.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.adapter.ActiveTraderShop_Adapter;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.AddShop_Model;

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

public class TraderActiveShops extends AppCompatActivity implements View.OnClickListener{
    private String DATA_URL=HOSTNAME+"active_shops_list.php";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<AddShop_Model>addShop_models;
    SharedPreferences sp;
    String traders_id="";
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    GButton gButton_nxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        setContentView(R.layout.activity_trader_active_shops);
        sp =getSharedPreferences("trader_id_sp", Context.MODE_PRIVATE);
        traders_id =sp.getString("traders_id",null);
        gButton_nxt =(GButton)findViewById(R.id.btn_finish);
        addShop_models = new ArrayList<>();
        recyclerView =(RecyclerView)findViewById(R.id.recy_active_tarder_shops_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActiveTraderShop_Adapter(addShop_models,getApplicationContext());
        recyclerView.setAdapter(adapter);
        fetch_active_trader_shop();
        gButton_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        //home = mToolbar.findViewById(R.id.home);
        // home.setOnClickListener(this);
        mToolbar.setTitle("");
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
//            case R.id.btn_finish:
//                Intent finish = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(finish);
//                // finish();
//                break;

        }

    }



    public void fetch_active_trader_shop()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL, new Response.Listener<String>() {
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
                        AddShop_Model addShop_model = new AddShop_Model(O.getString("trader_shop_id_list"),
                                O.getString("shop_name"),O.getString("image_name"));
                        addShop_models.add(addShop_model);
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e)
                {

                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("mtrader_id",traders_id);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }
}
