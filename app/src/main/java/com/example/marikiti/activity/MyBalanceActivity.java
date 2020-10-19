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
import android.widget.RelativeLayout;

import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class MyBalanceActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private LinearLayout ll_send_credit;
    private RelativeLayout ll_withdraw_fund, ll_transfer_funds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_my_balance);
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
        ll_withdraw_fund = findViewById(R.id.ll_withdraw_fund);
        ll_withdraw_fund.setOnClickListener(this);
        ll_send_credit = findViewById(R.id.ll_send_credit);
        ll_send_credit.setOnClickListener(this);
        ll_transfer_funds = findViewById(R.id.ll_transfer_funds);
        ll_transfer_funds.setOnClickListener(this);

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
            case R.id.ll_send_credit:
                Intent sendCredit = new Intent(mContext, MyBalanceSendCreditActivity.class);
                startActivity(sendCredit);
                break;
            case R.id.ll_withdraw_fund:
                Intent withdrawFunds = new Intent(mContext, MyBalanceWithdrawFundsActivity.class);
                startActivity(withdrawFunds);
                break;
            case R.id.ll_transfer_funds:
                Intent transferFunds = new Intent(mContext, MyBalanceTransferFundsActivity.class);
                startActivity(transferFunds);
                break;

        }

    }
}
