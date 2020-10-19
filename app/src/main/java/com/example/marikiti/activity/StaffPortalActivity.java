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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.Trader;
import com.example.marikiti.utilities.APP_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GEditText;
import gautam.easydevelope.widget.GTextView;

public class StaffPortalActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title,tv_register,tv_unregister;
    public static ImageView home;
    GButton next_btn;
    Trader model=new Trader();
    private TextView tv_date_of_birth;
    APP_URL appUrl=new APP_URL();
    Calendar calendar;
    int year, month, day;
    GEditText et_id_no,et_user_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_staff_portal);
        initToolbar();
        intiView();

    }


    public void intiView()
    {
        next_btn=findViewById(R.id.staff_portal_next);
        next_btn.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.staff_portal_next:
                startActivity(new Intent(getApplicationContext(),StaffPortalViewActivity.class));
                break;

            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.tv_date_of_birth:
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

    public void search_id(final String id_no,final String dob,final String user_code)
    {


        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);
        StringRequest request=new StringRequest(Request.Method.POST, appUrl.STAFF_PORTAL, new Response.Listener<String>() {
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


                                    model.setFull_name(jo.getString("full_name"));
                                    model.setDob(jo.getString("dob"));
                                    model.setId_no(jo.getString("id_no"));
                                    model.setUser_code(jo.getString("user_code"));
                                    model.setUser_name(jo.getString("user_name"));
                                    model.setUser_email(jo.getString("user_email"));
                                    model.setPhone_no(jo.getString("phone_no"));
                                    model.setAddress(jo.getString("address"));
                                    model.setProfile_pic(jo.getString("profile_pic"));


                                }



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



}
