package com.example.marikiti.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.MyAccountActivity;
import com.example.marikiti.activity.MyAccounts.RequestCreditActivity;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.fragment.FragmentNavigationDrawer;
import com.example.marikiti.utilities.Constant;

import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.utils.WindowUtils;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView mycart;
    private String txtType;
    TextView tv_cart;
    private Constant c=new Constant();
    private boolean doubleBackToExitPressedOnce = false;
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private LinearLayout ll_myprofile,ll_myaccount,ll_requestoverdraft,ll_applyloan,ll_buyinternet,ll_viewtrader,ll_addtrader,ll_staffportal,ll_setting,ll_customer_care,ll_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_home);
        prefs = Marikiti.getInstance().getPrefs();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initToolbar();
        findViewID();
        initDrawer();

      sp=getApplicationContext().getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
      ed=sp.edit();
        fetch_mycart();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Main Menu");
        mycart = mToolbar.findViewById(R.id.mycart);
        mycart.setOnClickListener(this);
        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);

        tv_cart=mToolbar.findViewById(R.id.cart_text);
    }

    private void findViewID() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initDrawer() {
        FragmentNavigationDrawer fragmentNavigationDrawer = new FragmentNavigationDrawer();
        fragmentNavigationDrawer.setOnDrawerItemInitListener(new FragmentNavigationDrawer.OnDrawerItemInitListener() {
            @Override
            public void onNavigationInit(View v) {
                onDrawerInit(v);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.navigation_drawer, fragmentNavigationDrawer).commit();
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        closeDrawer();


    }

    public void closeDrawer() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }


    @Override
    public void onBackPressed() {
        WindowUtils.with(mContext).hideSoftKeyboard(this);
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeDrawer();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    private void onDrawerInit(View v) {

        switch (v.getId()) {
            case R.id.ll_nav_my_profile:
                Intent myProfile = new Intent(mContext, MyProfileActivity.class);
                startActivity(myProfile);
                break;
            case R.id.ll_nav_my_account:
                Intent myAccount = new Intent(mContext, MyAccountActivity.class);
                startActivity(myAccount);
                break;
            case R.id.ll_nav_my_shop:
                Intent myShops = new Intent(mContext, MyShopsActivity.class);
                startActivity(myShops);
                break;
            case R.id.ll_nav_view_balance:
                Intent myBalance = new Intent(mContext, MyBalanceActivity.class);
                startActivity(myBalance);
                break;
            case R.id.ll_nav_request_credit:
                Intent reqCredit = new Intent(mContext, RequestCreditActivity.class);
                startActivity(reqCredit);
                break;
            case R.id.ll_nav_apply_loan:
                Intent applyLoan = new Intent(mContext, ApplyLoanActivity.class);
                startActivity(applyLoan);
                break;
            case R.id.ll_nav_buy_bundles:
                Intent buyBundle = new Intent(mContext, BuyInternetBundleActivity.class);
                startActivity(buyBundle);
                break;
            case R.id.ll_nav_view_trader:
                Intent viewTrader = new Intent(mContext, ViewTradersActivity.class);
                startActivity(viewTrader);
                break;
            case R.id.ll_nav_add_trader:
                Intent addTrader = new Intent(mContext, AddTraderActivity.class);
                startActivity(addTrader);
                break;
            case R.id.ll_nav_add_staff:
                Intent addStaff = new Intent(mContext, StaffPortalActivity.class);
                startActivity(addStaff);
                break;
            case R.id.ll_nav_setting_privacy:
                break;
            case R.id.ll_nav_help_center:
                break;
            case R.id.ll_nav_logout:
                new AlertDialog.Builder(mContext)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {

                                ed.clear();
                                ed.commit();
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                break;
        }
        closeDrawer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mycart:
                Intent mrcart = new Intent(mContext, MyCartActivity.class);
                startActivity(mrcart);
                break;
        }
    }

    public void fetch_mycart()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resposnse","response= "+response);
                tv_cart.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","total_no_item" );
                params.put("muser_id",sp.getString(c.USER_KEY,""));
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_mycart();
    }


}
