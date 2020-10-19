package com.example.marikiti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.model.Title;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class TraderDetailActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private String item;
Title model=new Title();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_trader_detail);
        initToolbar();
        findViewID();
    }


    private void initToolbar() {
        Intent intent = getIntent();
        item = intent.getStringExtra("item");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        try{
            title.setText(model.getTitle());
        }catch (NullPointerException e){}

        home = mToolbar.findViewById(R.id.home);
        home.setOnClickListener(this);
        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            if (item != null && item.equals("pizza")) {
                home.setImageDrawable(getResources().getDrawable(R.drawable.papa_jo));
                //getSupportActionBar().setLogo(R.drawable.papa_jo);
            } else if (item != null && item.equals("outlet")) {
                home.setImageDrawable(getResources().getDrawable(R.drawable.outlet));
                //getSupportActionBar().setLogo(R.drawable.outlet);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void findViewID() {
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
