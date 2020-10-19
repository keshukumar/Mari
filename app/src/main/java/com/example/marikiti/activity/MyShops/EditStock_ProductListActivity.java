package com.example.marikiti.activity.MyShops;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.marikiti.adapter.EditStockProductList_Adatper;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.ProductList_Model;
import com.example.marikiti.model.Stock;
import com.example.marikiti.utilities.APP_URL;
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

import static com.example.marikiti.utilities.APP_URL.SHOP_ITEM_LIST;

public class EditStock_ProductListActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_finish;
    private RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private TextView tv_title;
    private Stock s=new Stock();
    APP_URL u=new APP_URL();
    List<ProductList_Model> list=new ArrayList<>();
    private Constant c=new Constant();
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        setContentView(R.layout.activity_edit_stock__product_list);

        sp=mContext.getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
        ed=sp.edit();
        initToolbar();
        findViewID();
        setupList();
    }

    private void initToolbar()
    {
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

    private void findViewID()
    {
        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(this);
        tv_title=findViewById(R.id.productlist_title);
//      tx_trader_name=findViewById(R.id.editstock_trader_name);
//      tx_trader_code=findViewById(R.id.editstock_trader_code);
        try
        {
          tv_title.setText(s.getShop_name());

        }catch (NullPointerException e){}

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            //close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.home:
                finish();
                break;
            case R.id.btn_finish:
                finish();
                break;
         }

    }


    public void setupList()
    {
        recyclerView=findViewById(R.id.edit_recyclerview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(EditStock_ProductListActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        fetch_post();
    }

    public void fetch_post()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SHOP_ITEM_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response+sp.getString(c.TRADER_ID,""));
                if (response.equals("0"))
                {
                    Toast.makeText(mContext,"Data not Found !. Try Again",Toast.LENGTH_LONG).show();
                    finish();
                }else
                {    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                            ProductList_Model model=new ProductList_Model(o.getString("item_id"),o.getString("item_name"),o.getString("item_image"));
                            list.add(model);
                        }

                        adapter=new EditStockProductList_Adatper(list,EditStock_ProductListActivity.this,s.getTrader_shop_id());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        load.dismiss();
                      }catch(Exception e)
                      {
                        Log.d("response_error",e.toString());
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                      }
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d("response_error",error.toString());
                load.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String>params = new HashMap<>();
              // params.put("mtrader_id",sp.getString(c.TRADER_ID,""));
                params.put("mshop_id",s.getTrader_shop_id());
                Log.d("allid","id "+s.getTrader_shop_id()+"\n image"+s.getImage_name()+"\n name"+s.getShop_name());
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }

}
