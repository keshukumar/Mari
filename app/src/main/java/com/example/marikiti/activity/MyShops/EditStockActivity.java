package com.example.marikiti.activity.MyShops;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
import com.example.marikiti.adapter.EditStock_Adapter;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.EditStock_Model;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.FETCH_TRADER_ID;
import static com.example.marikiti.utilities.APP_URL.TRADER_DETAILS;

public class EditStockActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_finish;
    private RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private TextView tx_trader_name,tx_trader_code;
    private APP_URL u=new APP_URL();
    int[] imageList={R.drawable.fruits_50,R.drawable.vegetable_50,R.drawable.cereals_50
            ,R.drawable.butchery_50,R.drawable.dairy_50,R.drawable.bakery_50
            ,R.drawable.health_beauty_50,R.drawable.pharmacy_50};

    String[] nameList={"Fruits","Vegitable","Cereals","Butchery","Dariry","Bakery",
            "Health Beauty","Pharmacy"};

    List<EditStock_Model> list=new ArrayList<>();
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    Constant c=new Constant();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_edit_stock);
        sp=mContext.getSharedPreferences(c.USER_DETAILS, MODE_PRIVATE);
       ed=sp.edit();
        initToolbar();
        findViewID();
        setupList();

    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Edit Your Stock");
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

        tx_trader_name=findViewById(R.id.editstock_trader_name);
        tx_trader_code=findViewById(R.id.editstock_trader_code);


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
        recyclerView=findViewById(R.id.editstock_recyclerview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(EditStockActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        getTraderId();

    }

    public void fetch_post(final String trader_id)
    {

        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, u.EDIT_STOCK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","response"+response);
                if (response.equals("0"))
                {
                    Toast.makeText(mContext,"Data not Found !. Try Again",Toast.LENGTH_LONG).show();
                    finish();
                }else
                {
                    //   Log.d("response",response);
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                            EditStock_Model model=new EditStock_Model(o.getString("trader_shop_id"),o.getString("shop_name"),o.getString("image_name"));
                            list.add(model);
                            adapter=new EditStock_Adapter(EditStockActivity.this,list);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            load.dismiss();
                        }
                    }catch(Exception e)
                    {
                        Log.d("response_error",e.toString());
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d("response_error",error.toString());
                load.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("mtrader_id",trader_id);
                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }


    public void getTraderId()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        final StringRequest request=new StringRequest(Request.Method.POST, FETCH_TRADER_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response",response);
                if (!response.isEmpty())
                {
                    ed.putString(c.TRADER_ID,response);
                    ed.commit();
                    ed.apply();
                    fetch_post(response);
                    businessname(response);
                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Fail Login.! Connection fail.",Toast.LENGTH_LONG).show();
                load.dismiss();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put("muser_id",sp.getString(c.USER_KEY,""));
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);
    }



    public void businessname(final String trader_id)
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        final StringRequest request=new StringRequest(Request.Method.POST, TRADER_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response","bussiness "+response);
                if (!response.isEmpty())
                {
                    try {


                        JSONObject jsonObject = new JSONObject(response);

                        tx_trader_name.setText(jsonObject.getString("mbusiness_name"));
                        tx_trader_code.setText(jsonObject.getString("mtrader_code"));
                        load.dismiss();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Fail Login.! Connection fail.",Toast.LENGTH_LONG).show();
                load.dismiss();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put("mtrader_id",trader_id);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);
    }
}
