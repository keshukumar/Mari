package com.example.marikiti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

public class AddStaffActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private GButton btn_add_location;
    private LinearLayout ll_registered_staff, ll_unregistered_staff;
    private boolean staff = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_add_staff);
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
        ll_registered_staff = findViewById(R.id.ll_registered_staff);
        ll_registered_staff.setOnClickListener(this);
        ll_unregistered_staff = findViewById(R.id.ll_unregistered_staff);
        ll_unregistered_staff.setOnClickListener(this);
        btn_add_location = findViewById(R.id.btn_add_location);
        btn_add_location.setOnClickListener(this);

        staff = true;
        ll_registered_staff.setBackground(getResources().getDrawable(R.drawable.border_primary_round_corner));

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
            case R.id.ll_registered_staff:
                staff = true;
                ll_unregistered_staff.setBackground(null);
                ll_registered_staff.setBackground(getResources().getDrawable(R.drawable.border_primary_round_corner));
                break;
            case R.id.ll_unregistered_staff:
                staff = false;
                ll_registered_staff.setBackground(null);
                ll_unregistered_staff.setBackground(getResources().getDrawable(R.drawable.border_primary_round_corner));
                break;
            case R.id.btn_add_location:
                Intent addLocation = new Intent(mContext, AddLocationActivity.class);
                addLocation.putExtra("type", "staff");
                addLocation.putExtra("registered", staff);
                startActivity(addLocation);
                break;

        }

    }
}
