package com.example.marikiti.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.app.PermissionAllow;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Check_Connection;
import com.example.marikiti.utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.LOGINS;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private GTextView tv_not_yet_register;
    private GButton btn_login;
    private Context mContext;
    EditText et_email,et_password;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    Constant c=new Constant();
    static String email,password;
    APP_URL u=new APP_URL();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_login);
        PermissionAllow.allowAll(mContext, LoginActivity.this);
        sp=getApplicationContext().getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
        ed=sp.edit();
        initViews();

    }

    private void initViews() {
        btn_login = findViewById(R.id.btn_login);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        btn_login.setOnClickListener(this);
        tv_not_yet_register = (GTextView) findViewById(R.id.tv_not_yet_register);
        tv_not_yet_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login:
                if (!Check_Connection.isNetworkAvailable(LoginActivity.this))
                {
                    Toast.makeText(getApplicationContext(),c.ERROR_INTERNET,Toast.LENGTH_LONG).show();

                }else
                {

                    validate();
                }

                // Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                break;

            case R.id.tv_not_yet_register:
                Intent signUp = new Intent(mContext, SignUpActivity.class);
                startActivity(signUp);
                finish();
                break;
        }

    }

    public void validate()
    {
        email=et_email.getText().toString().trim();
        password=et_password.getText().toString().trim();

        if (email.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter email and password",Toast.LENGTH_LONG).show();
        }else
        {
            loginVolley();
        }
    }


    public void loginVolley()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        final StringRequest request=new StringRequest(Request.Method.POST, LOGINS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response","response"+response+"\nEmail and PAssword  " +email+password);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    Log.d("response","response"+status);

                    if (status.equals("1"))
                    {
                        String id=jsonObject.getString("user_id");
                        String role=jsonObject.getString("role");
                        ed.putString(c.USER_KEY,id);
                        ed.putString(c.STATUS,status);
                        ed.putString(c.ROLE,role);
                        ed.apply();
                        ed.commit();
                        Intent home = new Intent(mContext, HomeActivity.class);
                        startActivity(home);
                        Toast.makeText(mContext,"Successful Login",Toast.LENGTH_LONG).show();
                        load.dismiss();
                        finish();

                    }else if (status.equals("2"))
                    {
                        load.dismiss();
                        Toast.makeText(mContext,"Fail Login.! Wrong email or password.",Toast.LENGTH_LONG).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    load.dismiss();
                    Toast.makeText(mContext,"Fail Login.! Connection fail.",Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Fail Login.! Connection fail.",Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put(c.USER_EMAIL,email);
                params.put(c.USER_PASS,password);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setResult(Activity.RESULT_CANCELED);
    }


    @Override
    protected void onStart() {
        super.onStart();

        String status=sp.getString(c.USER_KEY,"");
        if (!status.isEmpty())
        {
            Toast.makeText(mContext,"Successful Login",Toast.LENGTH_LONG).show();
            Intent home = new Intent(mContext, HomeActivity.class);
            startActivity(home);
            finish();
        }
    }


}
