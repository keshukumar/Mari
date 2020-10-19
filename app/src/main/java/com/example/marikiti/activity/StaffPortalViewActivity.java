package com.example.marikiti.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class StaffPortalViewActivity extends AppCompatActivity implements View.OnClickListener {


    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private ImageView bimg1,bimg2,bimg3,bimg4,bimg5,bimg6,bimg7,bimg8,bimg9,bimg10,bimg11,bimg12,bimg13,bimg14,bimg15;
    SharedPreferences sp;
    String TRADER_ID;
    SharedPreferences.Editor ed;
    Constant c=new Constant();
    RelativeLayout trader_layout;
    TextView massage;
    ScrollView staff_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        setContentView(R.layout.activity_staff_portal_view);
        initToolbar();
        sp=getApplicationContext().getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
        ed=sp.edit();
        initView();
    }

    public void initView()
    {

     trader_layout=findViewById(R.id.trader_layout);
     massage=findViewById(R.id.massage);
     staff_layout=findViewById(R.id.stafflayout);

     fetch_traderName();

        bimg1=findViewById(R.id.bs_img1);
        bimg2=findViewById(R.id.bs_img2);
        bimg3=findViewById(R.id.bs_img3);
        bimg4=findViewById(R.id.bs_img4);
        bimg5=findViewById(R.id.bs_img5);
        bimg6=findViewById(R.id.bs_img6);
        bimg7=findViewById(R.id.bs_img7);
        bimg8=findViewById(R.id.bs_img8);
        bimg9=findViewById(R.id.bs_img9);
        bimg10=findViewById(R.id.bs_img10);
        bimg11=findViewById(R.id.bs_img11);
        bimg12=findViewById(R.id.bs_img12);
        bimg13=findViewById(R.id.bs_img13);
        bimg14=findViewById(R.id.bs_img14);
        bimg15=findViewById(R.id.bs_img15);
        bimg1.setOnClickListener(this);
        bimg2.setOnClickListener(this);
        bimg3.setOnClickListener(this);
        bimg4.setOnClickListener(this);
        bimg5.setOnClickListener(this);
        bimg6.setOnClickListener(this);
        bimg7.setOnClickListener(this);
        bimg8.setOnClickListener(this);
        bimg9.setOnClickListener(this);
        bimg10.setOnClickListener(this);
        bimg11.setOnClickListener(this);
        bimg12.setOnClickListener(this);
        bimg13.setOnClickListener(this);
        bimg14.setOnClickListener(this);
        bimg15.setOnClickListener(this);

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
        title.setText("Staff Portal");
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

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;

            case R.id.bs_img1:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img2:
                startActivity(new Intent(getApplicationContext(),AddShopsActivity.class));
                break;
            case R.id.bs_img3:
                checkTraderRegister();
                break;
            case R.id.bs_img4:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();

                break;
            case R.id.bs_img5:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img6:
                startActivity(new Intent(getApplicationContext(),Staff_ManageStaff_Activity.class));
                break;
            case R.id.bs_img7:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img8:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img9:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img10:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img11:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img12:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img13:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img14:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img15:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;

            default:
                Toast.makeText(getApplicationContext(),"service will be available soon.!",Toast.LENGTH_LONG).show();
                break;
        }

    }
    public void fetch_traderName()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","trader id="+sp.getString(c.USER_KEY,"")+"\n"+response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
//                        spinnerBussines.setText(object.getString("business_name"));
//                        spinnerUsercode.setText(object.getString("user_code"));
                        TRADER_ID=object.getString("trader_id");
                        if (TRADER_ID.length() > 0)
                        {
                        staff_layout.setVisibility(View.VISIBLE);
                        trader_layout.setVisibility(View.GONE);
                        }else
                        {
                            staff_layout.setVisibility(View.VISIBLE);
                            trader_layout.setVisibility(View.GONE);
                            massage.setText("To Access Staff Portal, you must be an employee of Intelligent Contacts Solutions Ltd");
                        }
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
                params.put("type","check_trader");
                params.put("muser_id",sp.getString(c.USER_KEY,""));
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }
    public void checkTraderRegister()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","trader id="+sp.getString(c.USER_KEY,"")+"\n"+response);
                if (response.equals("1"))
                {
                    Toast.makeText(getApplicationContext(), "You are already trader.", Toast.LENGTH_LONG).show();
                }else if (response.equals("2"))
                {
                    Intent addTrader = new Intent(mContext, AddTraderActivity.class);
                    startActivity(addTrader);
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
                params.put("type","check_trader_reg");
                params.put("muser_id",sp.getString(c.USER_KEY,""));
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }
}
