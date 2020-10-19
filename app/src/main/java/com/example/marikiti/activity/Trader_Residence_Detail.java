package com.example.marikiti.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.marikiti.R;
import com.example.marikiti.model.Trader;
import com.example.marikiti.utilities.Constant;

import gautam.easydevelope.widget.GEditText;
import gautam.easydevelope.widget.GTextView;

public class Trader_Residence_Detail extends AppCompatActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    public static GEditText et_country,et_constit,et_ward,et_road,et_housename,et_houseno;
    public CardView cv_next;
    public String country,constit,ward,road,houseno;
    private Constant c=new Constant();
    Trader model=new Trader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_trader__residence__detail);
        initToolbar();
        findViewid();
        setData();


    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Home Address");
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

    private void findViewid()
    {
        et_country=findViewById(R.id.et_resi_country);
        et_constit=findViewById(R.id.et_resi_constituency);
        et_ward=findViewById(R.id.et_resi_ward);
        et_housename=findViewById(R.id.et_resi_housename);
        et_houseno=findViewById(R.id.et_resi_houseNo);
        et_road=findViewById(R.id.et_resi_road);


        et_country.setOnClickListener(this);
        et_constit.setOnClickListener(this);
        et_ward.setOnClickListener(this);
        et_housename.setOnClickListener(this);
        et_houseno.setOnClickListener(this);
        et_road.setOnClickListener(this);


        cv_next=findViewById(R.id.cv_resi_next);
        cv_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.cv_resi_next:
               validation();
               break;

        }
    }

    public void validation()
    {

        country=et_country.getText().toString().trim();
        constit=et_constit.getText().toString().trim();
        ward=et_ward.getText().toString().trim();
        road=et_road.getText().toString().trim();
        houseno=et_houseno.getText().toString().trim();

        if (country.isEmpty())
        {
            et_country.setError(c.ERROR_COUNTRY);
            et_country.requestFocus();

        }else if (constit.isEmpty())
        {
            et_constit.setError(c.ERROR_CONSITI);
            et_constit.requestFocus();

        }else if (ward.isEmpty())
        {
            et_ward.setError(c.ERROR_WARD);
            et_ward.requestFocus();
        }else if (road.isEmpty())
        {
            et_road.setError(c.ERROR_ROAD);
            et_road.requestFocus();

        }else if (houseno.isEmpty())
        {
            et_houseno.setError(c.ERROR_HOUSENO);
            et_houseno.requestFocus();
        }else
        {
            Trader trader=new Trader();
            trader.setCountry(country);
            trader.setConstituency(constit);
            trader.setWard(ward);
            trader.setH_name(et_housename.getText().toString().trim());
            trader.setStreet(road);
            trader.setH_no(houseno);
            startActivity(new Intent(mContext,Trader_Bussiness_DetailActivity.class));
        }

    }


    public void setData()
    {
        et_country.setText(model.getCountry());
        et_constit.setText(model.getConstituency());
        et_ward.setText(model.getWard());
        et_houseno.setText(model.getH_no());
        et_housename.setText(model.getH_name());
        et_road.setText(model.getStreet());
    }
}
