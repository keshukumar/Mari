package com.example.marikiti.activity.MyAccounts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.marikiti.R;
import com.example.marikiti.activity.EditBusinessLocationActivity;
import com.example.marikiti.activity.MyShops.EditStockActivity;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.app.BaseActivity;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class MyShopsDetailActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private Spinner spn_duration;
    private LinearLayout ll_reg_new_shop, ll_my_fav_whole_seller, ll_edit_stock, ll_edit_b_location,
            ll_order_stock, ll_pause_shop, ll_delete_shop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_my_shops_detail);
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
        ll_reg_new_shop = findViewById(R.id.ll_reg_new_shop);
        ll_my_fav_whole_seller = findViewById(R.id.ll_my_fav_whole_seller);
        ll_edit_stock = findViewById(R.id.ll_edit_stock);
        ll_edit_b_location = findViewById(R.id.ll_edit_b_location);
        ll_order_stock = findViewById(R.id.ll_order_stock);
        ll_pause_shop = findViewById(R.id.ll_pause_shop);
        ll_delete_shop = findViewById(R.id.ll_delete_shop);

        ll_reg_new_shop.setOnClickListener(this);
        ll_my_fav_whole_seller.setOnClickListener(this);
        ll_edit_stock.setOnClickListener(this);
        ll_edit_b_location.setOnClickListener(this);
        ll_order_stock.setOnClickListener(this);
        ll_pause_shop.setOnClickListener(this);
        ll_delete_shop.setOnClickListener(this);
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

            case R.id.ll_edit_stock:
                Intent editStock = new Intent(mContext, EditStockActivity.class);
                startActivity(editStock);
                break;

            case R.id.ll_edit_b_location:
                Intent editLocation = new Intent(mContext, EditBusinessLocationActivity.class);
                startActivity(editLocation);
                break;

            case R.id.ll_reg_new_shop:
                break;

        }

    }
}
