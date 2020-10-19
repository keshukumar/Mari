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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.example.marikiti.R;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.Trader;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GEditText;
import gautam.easydevelope.widget.GTextView;

public class Trader_Bussiness_DetailActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    GButton next;
    APP_URL u=new APP_URL();
    private GEditText et_businessname,et_username,et_password,et_street,et_floor,et_businessno;

    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,checkTM;
    private APP_URL appUrl=new APP_URL();
    Constant c=new Constant();
    Trader model=new Trader();
    ArrayList<String> CountryList =new ArrayList<>();
    ArrayList<String> ConsiList =new ArrayList<>();
    ArrayList<String> WardList =new ArrayList<>();
    public Spinner countrySpinner,wardSpinner,consitSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        setContentView(R.layout.activity_trader__bussiness__detail);
        initToolbar();
        findViewbyId();

        setupSpinner();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void findViewbyId()
    {
        next=findViewById(R.id.btn_trader_business_next);
        next.setOnClickListener(this);
        et_businessname=findViewById(R.id.et_trader_bussiness_bname);
        et_username=findViewById(R.id.et_trader_bussiness_name);
        et_password=findViewById(R.id.et_trader_bussiness_password);
        countrySpinner=findViewById(R.id.spn_county);
        consitSpinner=findViewById(R.id.spn_constituency);
        wardSpinner=findViewById(R.id.spn_ward);
        et_street=findViewById(R.id.et_trader_bussiness_street);
        et_floor=findViewById(R.id.et_trader_bussiness_floor);
        et_businessno=findViewById(R.id.et_trader_bussiness_no);


        checkTM=findViewById(R.id.ck_trader_bussiness_check_tm);
        cb1=findViewById(R.id.ck_trader_bussiness_check1);
        cb2=findViewById(R.id.ck_trader_bussiness_check2);
        cb3=findViewById(R.id.ck_trader_bussiness_check3);
        cb4=findViewById(R.id.ck_trader_bussiness_check4);
        cb5=findViewById(R.id.ck_trader_bussiness_check5);
        cb6=findViewById(R.id.ck_trader_bussiness_check6);
        cb7=findViewById(R.id.ck_trader_bussiness_check7);
        cb8=findViewById(R.id.ck_trader_bussiness_check8);
        cb9=findViewById(R.id.ck_trader_bussiness_check9);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;

            case R.id.btn_trader_business_next:
                if (checkTM.isChecked())
                {
                    validate();
                }else
                {
                    Toast.makeText(getApplicationContext(),"Please check terms and conditions!",Toast.LENGTH_LONG).show();
                }


                break;

        }
    }

    public void addShop()
    {

        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);
        StringRequest request=new StringRequest(Request.Method.POST, appUrl.TRADER_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);


                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        JSONObject O =jsonArray.getJSONObject(0);
                        String sucess_mess =O.getString("success");
                        if(sucess_mess.equals("1"))
                        {
                            String trader_id =O.getString("trader_id");
                            SharedPreferences sps =getSharedPreferences("trader_id_sp",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor =sps.edit();
                            editor.putString("traders_id",trader_id);
                            editor.apply();
                            editor.commit();
                            load.dismiss();
                            Toast.makeText(getApplicationContext(),"Succesful Submit Data.!",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),AddShopsActivity.class).putExtra("tarder_id",trader_id));
                            finish();

                        }else if (sucess_mess.equals("2"))
                        {
                            Toast.makeText(getApplicationContext(),"Fail Submit Data.! try again.",Toast.LENGTH_LONG).show();
                            load.dismiss();
                        }
                    }catch(Exception e)
                    {

                            Log.d("error_mesage",e.toString());
                    }


                load.dismiss();

//

//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Fail Login. Internet Connection Fail.!",Toast.LENGTH_LONG).show();
                Log.d("response","error"+error.getMessage());
                load.dismiss();


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();

                params.put("muser_fullname",model.getFull_name());
                params.put("muser_email",model.getUser_email());
                params.put("muser_phone",model.getPhone_no());
                params.put("muser_id",model.getId_no());
                params.put("mdob",model.getDob());
                params.put("muser_name",model.getUser_name());
                params.put("m_pass","null");
                params.put("trader_id",model.getTrader_id());
                params.put("m_country",model.getCountry());
                params.put("m_consit",model.getConstituency());
                params.put("m_ward",model.getWard());
                params.put("m_street",model.getStreet());
                params.put("m_h_name",model.getH_name());
                params.put("m_h_number",model.getH_no());
                params.put("mtype",typeBussiness());
                params.put("mbusiness_name",et_businessname.getText().toString().trim());
                params.put("mbusi_user_name",et_username.getText().toString().trim());
                params.put("mbusiness_pass",et_password.getText().toString().trim());
                params.put("mbusiness_consit",consitSpinner.getSelectedItem().toString());
                params.put("mbusiness_country",countrySpinner.getSelectedItem().toString());
                params.put("mbusiness_ward",wardSpinner.getSelectedItem().toString());
                params.put("mbusiness_street",et_street.getText().toString());
                params.put("mbusiness_h_name",et_floor.getText().toString());
                params.put("mbusiness_no",et_businessno.getText().toString());
                params.put("mbuiness_pay_mode",paymentMode());
Log.d("keshav",  "\n"+("muser_fullname="+model.getFull_name())
                +"\n"+("muser_email="+model.getUser_email())
                +"\n"+("muser_phone="+model.getPhone_no())
                +"\n"+("muser_id="+model.getId_no())
                +"\n"+("mdob="+model.getDob())
                +"\n"+("muser_name="+model.getUser_name())
                +"\n"+("m_pass="+"")
                +"\n"+("trader_id="+model.getTrader_id())
                +"\n"+("m_country="+model.getCountry())
                +"\n"+("m_consit="+model.getConstituency())
                +"\n"+("m_ward="+model.getWard())
                +"\n"+("m_street="+model.getStreet())
                +"\n"+("m_h_name="+model.getH_name())
                +"\n"+("m_h_number="+model.getH_no())
                +"\n"+("mtype="+typeBussiness())
                +"\n"+("mbusiness_name="+et_businessname.getText().toString().trim())
                +"\n"+("mbusi_user_name="+et_username.getText().toString().trim())
                +"\n"+("mbusiness_pass="+et_password.getText().toString().trim())
                +"\n"+("mbusiness_consit="+consitSpinner.getSelectedItem().toString())
                +"\n"+("mbusiness_country="+countrySpinner.getSelectedItem().toString())
                +"\n"+("mbusiness_ward="+wardSpinner.getSelectedItem().toString())
                +"\n"+("mbusiness_street="+et_street.getText().toString())
                +"\n"+("mbusiness_h_name="+et_floor.getText().toString())
                +"\n"+("mbusiness_no="+et_businessno.getText().toString())
                        +"\n"+("mbuiness_pay_mode="+paymentMode()));
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);
    }

    public String typeBussiness()
    {
        String type="";
        if (cb1.isChecked())
        {
            type=cb1.getText().toString();

        }
        if (cb2.isChecked())
        {
            type=type+" , "+cb2.getText().toString();
        }
        if (cb3.isChecked())
        {
            type=type+" , "+cb3.getText().toString();
        }
        if (cb4.isChecked())
        {
            type=type+" , "+cb4.getText().toString();
        }

        return type;

    }

    public void validate()
    {
        String bname=et_businessname.getText().toString().trim();
        String username=et_username.getText().toString();
        String password=et_password.getText().toString();
        String street=et_street.getText().toString();
        String no=et_businessno.getText().toString();
        String ward=wardSpinner.getSelectedItem().toString();
        String con=consitSpinner.getSelectedItem().toString();

        String type=typeBussiness();
        if (con.isEmpty()&& con.equals("Select Constituency"))
        {
            Toast.makeText(mContext,"Please Select Constituency.!",Toast.LENGTH_LONG).show();

        }else if (ward.isEmpty()&& ward.equals("Select Ward"))
        {
            Toast.makeText(mContext,"Please Select Ward.!",Toast.LENGTH_LONG).show();

        }
        if (type.isEmpty())
        {
            Toast.makeText(mContext,"Please check Bussiness Type.!",Toast.LENGTH_LONG).show();

        }else if(bname.isEmpty()&&username.isEmpty()&&password.isEmpty()&&street.isEmpty()&&no.isEmpty())
        {
            Toast.makeText(mContext,"Please fill all fields.!",Toast.LENGTH_LONG).show();

        }else
        {
           // Log.d("response",paymentMode()+" \ntype"+typeBussiness());
            addShop();
        }
    }

    public String paymentMode()
    {
        String type="";
        if (cb5.isChecked())
        {
            type=cb5.getText().toString();
        }
        if (cb6.isChecked())
        {
            type=type+" , "+cb6.getText().toString();
        }
        if (cb7.isChecked())
        {
            type=type+" , "+cb7.getText().toString();
        }
        if (cb8.isChecked())
        {
            type=type+" , "+cb8.getText().toString();
        }
        if (cb9.isChecked())
        {
            type=type+" , "+cb9.getText().toString();
        }

        return type;
    }


    public void setupSpinner()
    {
        loadCountrySpinnerData();
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country=countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString();
                loadConsiSpinnerData(country);
                // Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        consitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=consitSpinner.getItemAtPosition(consitSpinner.getSelectedItemPosition()).toString();

                loadWardSpinnerData(country);
                //Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        wardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=wardSpinner.getItemAtPosition(wardSpinner.getSelectedItemPosition()).toString();

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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, u.COUNTRY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    CountryList.clear();
                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("country_name");
                        CountryList.add(country);
                        Log.d("country","\n"+ CountryList.get(i));

                    }
                    countrySpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, CountryList));

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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, u.CONSI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    ConsiList.clear();
                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("consistency_name");

                        ConsiList.add(country);
                        Log.d("country","\n"+ ConsiList.get(i));

                    }
                    consitSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, ConsiList));
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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, u.WARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    WardList.clear();
                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("ward_name");

                        WardList.add(country);
                        Log.d("country","\n"+WardList.get(i));

                    }
                    wardSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, WardList));
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
