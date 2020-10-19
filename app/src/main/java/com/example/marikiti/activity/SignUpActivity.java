package com.example.marikiti.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;

import java.util.Calendar;

import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;


public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private GTextView tv_allready_register;
    private TextView tv_date_of_birth;
    private EditText et_full_name, et_mobile, et_id_number, et_email, et_user_name, et_password;
    private GButton btn_next;
    private Context mContext;
    Calendar calendar;
    int year, month, day;
    Constant c=new Constant();
    APP_URL u=new APP_URL();
    static String full_name,name,id,email,password,phone,dob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_signup);
        initViews();




    }

    private void initViews() {
        et_full_name = findViewById(R.id.et_full_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_id_number = findViewById(R.id.et_id_number);
        tv_date_of_birth = findViewById(R.id.tv_date_of_birth);
        tv_date_of_birth.setOnClickListener(this);
        et_email = findViewById(R.id.et_email);
        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        tv_allready_register = (GTextView) findViewById(R.id.tv_allready_register);
        tv_allready_register.setOnClickListener(this);

    }

    public void validation()
    {
        full_name=et_full_name.getText().toString().trim();
        name=et_user_name.getText().toString().trim();
        phone=et_mobile.getText().toString().trim();
        email=et_email.getText().toString().trim();
        password=et_password.getText().toString().trim();
        dob=tv_date_of_birth.getText().toString();
        id=et_id_number.getText().toString().trim();

        if (full_name.isEmpty())
        {
            et_full_name.setError(c.ERROR_FULLNAME);
            et_full_name.requestFocus();
        }else if (phone.isEmpty())
        {
            et_mobile.setError(c.ERROR_MOBILE);
            et_mobile.requestFocus();

        }else if (phone.length()<10)
        {
            et_mobile.setError("Please enter valid digit mobile no.");
            et_mobile.requestFocus();

        }else if (id.isEmpty())
        { et_id_number.setError(c.ERROR_ID);
            et_id_number.requestFocus();
        }else if (dob.isEmpty())
        {
            Toast.makeText(mContext,"Please select Date of Birth.!",Toast.LENGTH_LONG).show();
        }else if (email.isEmpty()) {
            et_email.setError(c.ERROR_EMAIL);
            et_email.requestFocus();
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
            Intent addLocation = new Intent(mContext, AddLocationActivity.class);
            addLocation.putExtra("type", "customer");
            addLocation.putExtra(c.USER_FULLNAME,full_name);
            addLocation.putExtra(c.USER_NAME,name);
            addLocation.putExtra(c.USER_ID,id);
            addLocation.putExtra(c.USER_EMAIL,email);
            addLocation.putExtra(c.USER_PASS,password);
            addLocation.putExtra(c.USER_PHONE,phone);
            addLocation.putExtra(c.USER_DOB,dob);
            startActivity(addLocation);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_date_of_birth:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                tv_date_of_birth.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
                break;

            case R.id.btn_next:
                validation();
                break;

            case R.id.tv_allready_register:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setResult(Activity.RESULT_CANCELED);
    }
}
