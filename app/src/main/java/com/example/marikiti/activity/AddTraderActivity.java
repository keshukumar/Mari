package com.example.marikiti.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.Trader;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GEditText;
import gautam.easydevelope.widget.GTextView;

public class AddTraderActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title,tv_register,tv_unregister;
    public static ImageView home;
    Trader model =new Trader();
    private Button btn_unregister_next,btn_register_next;
    private GCircularImageView iv_profilepic;
    private EditText et_full_name, et_mobile, et_id_number, et_email, et_user_name, et_password,et_bussiness_name,
            t_fullname,t_id_no,t_dob,t_mkt_no,t_username,t_email,t_phone,t_address;

    static String full_name,name,id,email,password,phone,bussiness_name,dob,mkt_no,address;
    Constant c=new Constant();
    private LinearLayout ll_registerLayoutBtn, ll_unregisterLayoutBtn,ll_register,ll_unregister;
    private CardView cv_nextlayout;
    APP_URL appUrl=new APP_URL();

    Dialog dialogs;
    LinearLayout ll_code,ll_phone;
    String RECIEVED_OTP,country;
    CardView ll_validate_code,ll_validate_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_add_trader);
        initToolbar();
        findViewID_Unrigistered();
        findViewId_Registered();

        try {
            String status=getIntent().getStringExtra("status");
            if (status.equals("register"))
            {
                registerSetup();
            }
        }catch (NullPointerException e){}
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Trader Details");
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

    private void findViewId_Registered()
    {
        btn_register_next=findViewById(R.id.btn_trader_register_next);
        btn_register_next.setOnClickListener(this);

        ll_registerLayoutBtn=findViewById(R.id.ll_trader_register_user);
        ll_registerLayoutBtn.setOnClickListener(this);
        ll_register=findViewById(R.id.ll_trader_register_layout);

        t_fullname=findViewById(R.id.et_trader_register_fullname);
        t_id_no=findViewById(R.id.et_trader_register_id_no);
        t_dob=findViewById(R.id.et_trader_register_dob);
        t_mkt_no=findViewById(R.id.et_trader_register_mkt_userno);
        t_username=findViewById(R.id.et_trader_register_username);
        t_email=findViewById(R.id.et_trader_register_emailaddress);
        t_address=findViewById(R.id.et_trader_register_address);
        t_phone=findViewById(R.id.et_trader_register_phoneno);

        tv_register=findViewById(R.id.tv_trade_register);
        tv_unregister=findViewById(R.id.tv_trade_unregister);

        iv_profilepic=findViewById(R.id.iv_trader_register_pic);



    }


    private void findViewID_Unrigistered() {
        btn_unregister_next = findViewById(R.id.btn_add_location);
        btn_unregister_next.setOnClickListener(this);


        et_full_name=findViewById(R.id.add_tr_fullname);
        et_email=findViewById(R.id.add_tr_email);
        et_mobile=findViewById(R.id.add_tr_phone);
        et_id_number=findViewById(R.id.add_tr_id);
        et_user_name=findViewById(R.id.add_tr_username);
        et_bussiness_name=findViewById(R.id.add_tr_bussinessname);
        et_password=findViewById(R.id.add_tr_password);


        ll_unregisterLayoutBtn =findViewById(R.id.ll_trader_unregister_user);
        ll_unregisterLayoutBtn.setOnClickListener(this);
        ll_unregister=findViewById(R.id.ll_trader_unregister_layout);
        cv_nextlayout=findViewById(R.id.cv_trader_nextlayout);
        cv_nextlayout.setOnClickListener(this);


    }

    public void validationRegistered()
    {
        full_name=t_fullname.getText().toString().trim();
        id=t_id_no.getText().toString().trim();
        dob=t_dob.getText().toString().trim();
        mkt_no=t_mkt_no.getText().toString().trim();
        name=t_username.getText().toString().trim();
        email=t_email.getText().toString().trim();
        phone=t_phone.getText().toString().trim();


        if (full_name.isEmpty())
        {
            t_fullname.setError(c.ERROR_FULLNAME);
            t_fullname.requestFocus();

        }else if (id.isEmpty())
        {
            t_id_no.setError(c.ERROR_ID);
            t_id_no.requestFocus();

        }else if (dob.isEmpty())
        {
            t_dob.setError(c.ERROR_DOB);
            t_dob.requestFocus();

        }else if (name.isEmpty())
        {
            t_username.setError(c.ERR0R_NAME);
            t_username.requestFocus();

        }else if (mkt_no.isEmpty())
        {
            t_mkt_no.setError(c.ERROR_MKT_NO);
            t_mkt_no.requestFocus();

        }else if (email.isEmpty())
        {
            t_email.setError(c.ERROR_EMAIL);
            t_email.requestFocus();

        }else if (phone.isEmpty())
        {
            t_phone.setError(c.ERROR_MOBILE);
            t_phone.requestFocus();

        }
        else
        {

            model.setFull_name(full_name);
            model.setUser_name(name);
            model.setId_no(id);
            model.setDob(dob);
            model.setMkt_no(mkt_no);
            model.setUser_email(email);
            model.setPhone_no(phone);
            model.setAddress(address);
            Intent addLocation = new Intent(mContext, Trader_Residence_Detail.class);
            startActivity(addLocation);

        }
    }




    public void validationUnregistered()
    {
        full_name=et_full_name.getText().toString().trim();
        name=et_user_name.getText().toString().trim();
        phone=et_mobile.getText().toString().trim();
        email=et_email.getText().toString().trim();
        password=et_password.getText().toString().trim();
        id=et_id_number.getText().toString().trim();
        bussiness_name=et_bussiness_name.getText().toString();
        //   String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (full_name.isEmpty())
        {
            et_full_name.setError(c.ERROR_FULLNAME);
            et_full_name.requestFocus();

        }else if (email.isEmpty()) {
            et_email.setError(c.ERROR_EMAIL);
            et_email.requestFocus();
        }else if (phone.isEmpty())
        {
            et_mobile.setError(c.ERROR_MOBILE);
            et_mobile.requestFocus();

        }else if (id.isEmpty())
        { et_id_number.setError(c.ERROR_ID);
            et_id_number.requestFocus();
        }else if (bussiness_name.isEmpty())
        { et_bussiness_name.setError(c.ERROR_ID);
            et_bussiness_name.requestFocus();
        }else if (name.isEmpty())
        {
            et_user_name.setError(c.ERR0R_NAME);
            et_user_name.requestFocus();
        }else if (password.isEmpty())
        {
            et_password.setError(c.ERROR_PASSWORD);
            et_password.requestFocus();
        }else
        {
            model.setFull_name(full_name);
            model.setUser_name(name);
            model.setId_no(id);;
            model.setUser_email(email);
            model.setPhone_no(phone);
            model.setPassword(password);
            model.setBusinessName(bussiness_name);

            dialogs=new Dialog(this);
            dialogs.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialogs.setContentView(R.layout.inflate_trader_unregister_otp);
            dialogs.show();

            final GEditText et_otp=dialogs.findViewById(R.id.et_trader_unregister_otp);
            final String code=et_otp.getText().toString().trim();

            ll_code=dialogs.findViewById(R.id.code_layout);
            ll_phone=dialogs.findViewById(R.id.phone_layout);
            final TextView p=dialogs.findViewById(R.id.phone);
            p.setText(phone);

            ll_validate_code=dialogs.findViewById(R.id.validate_code);
            ll_validate_number=dialogs.findViewById(R.id.validate_layout);
            GButton button=dialogs.findViewById(R.id.btn_trader_unregister_otp);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Spinner sp=dialogs.findViewById(R.id.sp_trader_country);
                    country=sp.getSelectedItem().toString().trim();
                    if (country.equals("Select Your Country"))
                    {
                        Toast.makeText(mContext,"Please Select Your Country .!",Toast.LENGTH_LONG).show();
                    }else if (country.length()<1)
                    {
                        Toast.makeText(mContext,"Please Select Your Country .!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        ll_validate_number.setVisibility(View.GONE);
                        ll_validate_code.setVisibility(View.VISIBLE);
                        ll_code.setVisibility(View.VISIBLE);
                        //otpVolley(country,phone,code);
                    }

                }
            });

            ll_validate_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent addLocation = new Intent(mContext,Trader_Unregister_Residence.class);
                    startActivity(addLocation);
                    Toast.makeText(mContext,"Successful Validation",Toast.LENGTH_LONG).show();
                    dialogs.dismiss();
//                    if (RECIEVED_OTP.equals(code))
//                    {
//                        Intent addLocation = new Intent(mContext,Trader_Unregister_Residence.class);
//                        startActivity(addLocation);
//                        Toast.makeText(mContext,"Successful Validation",Toast.LENGTH_LONG).show();
//                        dialogs.dismiss();
//
//
//                    }else
//                    {
//
//                        Toast.makeText(mContext,"Enter Four Digit Security Code Mis-Match.! Try Again",Toast.LENGTH_LONG).show();
//                    }
                }
            });




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

            case R.id.btn_add_location:
                validationUnregistered();


                break;

            case R.id.ll_trader_register_user:
                registerSetup();
                break;

            case R.id.ll_trader_unregister_user:
                ll_unregister.setVisibility(View.VISIBLE);
                ll_register.setVisibility(View.GONE);
                cv_nextlayout.setVisibility(View.VISIBLE);
                ll_registerLayoutBtn.setBackgroundResource(R.drawable.border_round_corner);
                ll_unregisterLayoutBtn.setBackgroundResource(R.drawable.border_round_corner_primary);
                tv_register.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_unregister.setTextColor(getResources().getColor(R.color.colorWhite));
                break;

            case R.id.btn_trader_register_next:
                validationRegistered();
                break;

        }

    }



    public void search_id(final String id, final Dialog dialog)
    {


        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);
        StringRequest request=new StringRequest(Request.Method.POST, appUrl.TRADER_SEARCH_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);

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

                            }else
                            {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jo=jsonArray.getJSONObject(i);

                                    model.setFull_name(jo.getString(c.USER_FULLNAME));
                                    model.setPhone_no(jo.getString(c.USER_PHONE));
                                    model.setId_no(jo.getString(c.ID_NO));
                                    model.setUser_email(jo.getString(c.USER_EMAIL));
                                    model.setUser_name(jo.getString(c.USER_NAME));
                                    model.setDob(jo.getString(c.USER_DOB));
                                    model.setCountry(jo.getString(c.USER_COUNTRY));
                                    model.setConstituency(jo.getString(c.USER_CONSTI));
                                    model.setWard(jo.getString(c.USDER_WARD));
                                    model.setStreet(jo.getString(c.USER_STREET));
                                    model.setH_name(jo.getString(c.USER_HOUSE_NAME));
                                    model.setH_no(jo.getString(c.USER_HOUSE_NUMBER));
                                    model.setUser_code(jo.getString(c.USER_CODE));
                                    model.setTrader_id(jo.getString("trader_id"));
                                    model.setProfile_pic(jo.getString("profile_pic"));

                                }

//                                ll_unregisterLayoutBtn.setBackgroundResource(R.drawable.border_round_corner);
//                                ll_registerLayoutBtn.setBackgroundResource(R.drawable.border_round_corner_primary);
                                ll_unregisterLayoutBtn.setVisibility(View.GONE);
                                ll_registerLayoutBtn.setVisibility(View.GONE);
                                tv_unregister.setTextColor(getResources().getColor(R.color.colorBlack));
                                tv_register.setTextColor(getResources().getColor(R.color.colorWhite));
                                ll_register.setVisibility(View.VISIBLE);
                                cv_nextlayout.setVisibility(View.VISIBLE);
                                setData();
                                dialog.dismiss();
                                load.dismiss();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
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
                dialog.dismiss();
                load.dismiss();


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put(c.ID_NO,id);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);
    }

    public void setData()
    {
        t_fullname.setText(model.getFull_name());
        t_username.setText(model.getUser_name());
        t_phone.setText(model.getPhone_no());
        t_id_no.setText(model.getId_no());
        t_dob.setText(model.getDob());
        t_mkt_no.setText(model.getUser_code());
        t_email.setText(model.getUser_email());



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


    public void registerSetup()
    {
        ll_unregister.setVisibility(View.GONE);
        cv_nextlayout.setVisibility(View.INVISIBLE);
        //....Show Search id dilog.......
        final Dialog dialog=new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.inflate_trader_register_search_id);
        dialog.show();
        final GEditText editText=dialog.findViewById(R.id.et_trader_search_id);
        GButton gButton=dialog.findViewById(R.id.btn_trader_register_search);
        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_id(editText.getText().toString().trim(),dialog);

            }
        });
    }

    public void otpVolley(final String country,final String phone,final String enterOtp)
    {

        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);
        StringRequest request=new StringRequest(Request.Method.POST, appUrl.TRADER_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    Log.d("validation","otp="+response);
                    jsonObject = new JSONObject(response);
                    String server=jsonObject.getString("server_response");
                    JSONArray jsonArray =jsonObject.getJSONArray("sms_code");
                    JSONObject object =jsonArray.getJSONObject(0);
                    String otp=object.getString("");
                    Log.d("validation","otp="+otp);
                    if (otp.length()>1)
                    {
                        RECIEVED_OTP=otp;
                        load.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialogs.dismiss();
                    Toast.makeText(mContext,"Internet Error.! Try Again",Toast.LENGTH_LONG).show();
                    load.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                load.dismiss();


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put("phone_no",phone);
                params.put("country_name",country);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);

    }

}
