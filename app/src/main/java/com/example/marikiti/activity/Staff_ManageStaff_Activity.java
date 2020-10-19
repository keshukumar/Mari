package com.example.marikiti.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.Trader;
import com.example.marikiti.utilities.APP_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GEditText;
import gautam.easydevelope.widget.GTextView;

public class Staff_ManageStaff_Activity extends AppCompatActivity implements View.OnClickListener   {
    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    GButton next_btn;
    Trader model=new Trader();
    private GTextView tv_date_of_birth;
    APP_URL appUrl=new APP_URL();
    Calendar calendar;
    int year, month, day;
    private GCircularImageView iv_profilepic;
    private GTextView t_fullname,t_id_no,t_dob,t_mkt_no,t_username,t_email,t_phone,t_address,et_country,et_constit,et_ward;
    GEditText et_id_no,et_user_code,et_saletraget;
    GButton btn_next,btn_finish;
    Spinner sp_country,sp_consi,sp_ward;
    LinearLayout ll_search,ll_showdata,ll_residance;
    APP_URL url=new APP_URL();
    ArrayList<String> CountryList =new ArrayList<>();
    ArrayList<String> ConsiList =new ArrayList<>();
    ArrayList<String> WardList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_staff__manage_staff_);
        initToolbar();
        intiView();

        setupSpinner();


    }


    public void intiView()
    {
        next_btn=findViewById(R.id.staff_portal_next);
        next_btn.setOnClickListener(this);
        et_id_no=findViewById(R.id.staff_manage_id);
        tv_date_of_birth=findViewById(R.id.staff_manage_dob);
        et_user_code=findViewById(R.id.staff_manage_marikiti);

        tv_date_of_birth.setOnClickListener(this);

        iv_profilepic=findViewById(R.id.iv_trader_register_pic);


        t_fullname=findViewById(R.id.et_trader_register_fullname);
        t_id_no=findViewById(R.id.et_trader_register_id_no);
        t_dob=findViewById(R.id.et_trader_register_dob);
        t_mkt_no=findViewById(R.id.et_trader_register_mkt_userno);
        t_username=findViewById(R.id.et_trader_register_username);
        t_email=findViewById(R.id.et_trader_register_emailaddress);
        t_address=findViewById(R.id.et_trader_register_address);
        t_phone=findViewById(R.id.et_trader_register_phoneno);


        ll_search=findViewById(R.id.ll_staff_searchstaff);
        ll_showdata=findViewById(R.id.ll_staff_register_layout);
        ll_residance=findViewById(R.id.ll_staff_residance);


        et_country=findViewById(R.id.et_resi_country);
        et_constit=findViewById(R.id.et_resi_constituency);
        et_ward=findViewById(R.id.et_resi_ward);
        et_saletraget=findViewById(R.id.staff_manage_sales_target);

        //et_saletraget=findViewById(R.id.et_sales_target);

        btn_finish=findViewById(R.id.btn_staff_finish);
        btn_next=findViewById(R.id.btn_trader_register_next);
        btn_next.setOnClickListener(this);
        btn_finish.setOnClickListener(this);

        sp_country=findViewById(R.id.staff_spn_county);
        sp_consi=findViewById(R.id.staff_spn_constituency);
        sp_ward=findViewById(R.id.staff_spn_ward);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_staff_finish:
                finish();
                break;

            case R.id.btn_trader_register_next:
                loadCountrySpinnerData();
                ll_residance.setVisibility(View.VISIBLE);
                ll_showdata.setVisibility(View.GONE);
                ll_search.setVisibility(View.GONE);

                break;

            case R.id.staff_portal_next:
                validation();
                break;

            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.staff_manage_dob:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //sets date in EditText
                                tv_date_of_birth.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
                break;


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
        title.setText("Manage Staff");
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

    public void search_id(final String id_no,final String dob,final String user_code)
    {


        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);
        final StringRequest request=new StringRequest(Request.Method.POST, appUrl.STAFF_PORTAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","ifno"+id_no+response);

                if (!response.isEmpty())
                {
                    if (response.equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"Not Found Search Data.!.",Toast.LENGTH_LONG).show();
                    }else
                    {
                        try {
                            if (response.equals("0"))
                            {
                                Toast.makeText(getApplicationContext(),"Search Data Not Found!",Toast.LENGTH_LONG).show();
                                load.dismiss();
                            }else
                            {
                                JSONObject jo=new JSONObject(response);

                                model.setFull_name(jo.getString("full_name"));
                                model.setDob(jo.getString("dob"));
                                model.setId_no(jo.getString("id_no"));
                                model.setUser_code(jo.getString("user_code"));
                                model.setUser_name(jo.getString("user_name"));
                                model.setUser_email(jo.getString("user_email"));
                                model.setPhone_no(jo.getString("phone_no"));
                                model.setAddress(jo.getString("address"));
                                model.setProfile_pic(jo.getString("profile_pic"));
                                setData();
                                load.dismiss();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();

                            load.dismiss();
                            Log.d("response","Error = "+e.getMessage());
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }

//

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Fail Login. Internet Connection Fail.!",Toast.LENGTH_LONG);

                load.dismiss();


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new HashMap<>();
                params.put("muser_id",id_no);
                params.put("user_code",user_code);
                params.put("mdob",dob);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);
    }


    public void validation()
    {
        String user_id=et_id_no.getText().toString().trim();
        String user_code=et_user_code.getText().toString().trim();
        String dob=tv_date_of_birth.getText().toString().trim();

        if (user_code.length()<1)
        {
            et_user_code.setError("Please enter valid marikiti no.!");
            et_user_code.requestFocus();

        }else if (user_id.length()<1)
        {
            et_id_no.setError("Please enter valid id no.!");
            et_id_no.requestFocus();
        }else if (dob.length()<1)
        {
            Toast.makeText(mContext,"Please select date of birth.!",Toast.LENGTH_LONG).show();
        }else if (dob.equals("Select Date of Birth:"))
        {
            Toast.makeText(mContext,"Please select date of birth.!",Toast.LENGTH_LONG).show();
        }else
        {
            search_id(user_id,dob,user_code);
        }

    }

    public void setData()
    {

        ll_showdata.setVisibility(View.VISIBLE);
        ll_search.setVisibility(View.GONE);
        t_fullname.setText(model.getFull_name());
        t_username.setText(model.getUser_name());
        t_phone.setText(model.getPhone_no());
        t_id_no.setText(model.getId_no());
        t_dob.setText(model.getDob());
        t_mkt_no.setText(model.getUser_code());
        t_email.setText(model.getUser_email());
        t_address.setText(model.getAddress());



        try

        {

            if (model.getProfile_pic().equals("null"))
            {
                iv_profilepic.setBackgroundResource(R.drawable.empty_profile);
            }else
            {
                try { Glide.with(mContext).load(appUrl.TRADER_PROFILE_PIC+model.getProfile_pic()).into(iv_profilepic); }catch (Exception e){}

            }
        }catch (NullPointerException e)
        {
            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
            Log.d("keshav",model.getProfile_pic());
        }



    }




    public void setupSpinner()
    {
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country=sp_country.getItemAtPosition(sp_country.getSelectedItemPosition()).toString();
                loadConsiSpinnerData(country);
               // Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_consi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=sp_consi.getItemAtPosition(sp_consi.getSelectedItemPosition()).toString();

                loadWardSpinnerData(country);
                //Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        sp_ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=sp_ward.getItemAtPosition(sp_ward.getSelectedItemPosition()).toString();

               // Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }


    private void loadCountrySpinnerData() {
        final ProgressDialog load =ProgressDialog.show(mContext,"Loading.","Please Wait..",false,false);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url.COUNTRY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("country_name");
                        CountryList.add(country);
                        Log.d("country","\n"+ CountryList.get(i));

                    }
                    sp_country.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, CountryList));

                    load.dismiss();  }catch (JSONException e){e.printStackTrace();
                    load.dismiss();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadConsiSpinnerData(final String country) {
        final ProgressDialog load =ProgressDialog.show(mContext,"Loading.","Please Wait..",false,false);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url.CONSI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("consistency_name");

                        ConsiList.add(country);
                        Log.d("country","\n"+ ConsiList.get(i));

                    }
                    sp_consi.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, ConsiList));
                    load.dismiss(); }catch (JSONException e){e.printStackTrace();
                    load.dismiss();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new HashMap<>();
                params.put("mcountry_name",country);

                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
    private void loadWardSpinnerData(final String consi) {
        final ProgressDialog load =ProgressDialog.show(mContext,"Loading.","Please Wait..",false,false);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url.WARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("ward_name");

                        WardList.add(country);
                        Log.d("country","\n"+WardList.get(i));

                    }
                    sp_ward.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, WardList));
                    load.dismiss();  }catch (JSONException e){e.printStackTrace();
                    load.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        })
        {
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new HashMap<>();
                params.put("mconsistency",consi);

                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
