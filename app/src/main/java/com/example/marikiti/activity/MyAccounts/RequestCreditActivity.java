package com.example.marikiti.activity.MyAccounts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.marikiti.R;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.app.BaseActivity;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class RequestCreditActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private Spinner spn_employement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_request_credit);
        initToolbar();
        findViewID();
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

    private void findViewID() {
        spn_employement = findViewById(R.id.spn_employement);
        spn_employement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // do something upon option selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });


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
        }

    }
}
